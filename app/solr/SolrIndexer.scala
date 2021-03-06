package solr

import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.ws.{Response, WS}
import models.base.{Description, DescribedEntity, AccessibleEntity}
import play.api.libs.json._
import models._

import play.Play.application

import defines.EnumUtils.enumWrites
import concurrent.Future
import rest.{ServerError, ValidationError, RestDAO, RestError}
import play.api.Logger
import models.Repository
import play.api.libs.json.JsObject
import models.DocumentaryUnit
import defines.EntityType
import play.api.libs.iteratee.Input.Empty
import play.api.i18n.Lang

/**
 * Object containing functions for managing the Solr index.
 * Most of this should eventually go away when we have a
 * proper external indexing service.
 */
object SolrIndexer extends RestDAO {

  import SolrConstants._

  // We don't need a user here yet unless we want to log
  // when the Solr index is changed.
  val userProfile: Option[UserProfile] = None
  private def updateUrl(commit: Boolean = true) = {
    "%s/update?wt=json&commit=%s&optimize=%s".format(application.configuration.getString("solr.path"), commit, commit)
  }
  private val batchSize = application.configuration.getInt("solr.update.batchSize")

  override val headers = Map(
    "Accept" -> "application/json",
    "Content-Type" -> "application/json; charset=utf8"
  )

  /**
   * Possible responses from Solr calls.
   */

  case class SolrError(msg: String, code: Int)
  implicit val solrErrorReads = Json.reads[SolrError]

  case class SolrHeader(status: Int, time: Int)
  import play.api.libs.functional.syntax._
  implicit val solrHeaderReads: Reads[SolrHeader] = (
    (__ \ "status").read[Int] and
    (__ \ "QTime").read[Int]
  )(SolrHeader.apply _)

  sealed trait SolrResponse
  case class SolrErrorResponse(err: String) extends SolrResponse
  case class SolrUpdateResponse(responseHeader: SolrHeader, error: Option[SolrError] = None) extends SolrResponse
  implicit val solrUpdateResponseReads: Reads[SolrUpdateResponse] = (
    (__ \ "responseHeader").read[SolrHeader] and
    (__ \ "error").readNullable[SolrError]
  )(SolrUpdateResponse.apply _)

  /**
   * Commit all pending docs
   */
  def commit: Future[SolrResponse] = solrUpdate(Json.obj(), commit = true)

  /**
   * Delete a list of Solr models.
   */
  def deleteItemsById(items: Stream[String], commit: Boolean = true): Future[SolrResponse] = {
    // NB: Solr delete syntax requires a MAP rather than a list
    val deleteJson = items.foldLeft(Json.obj()) { case (obj,id) =>
      // NB: Because we delete logical items, but descriptions are indexed
      // we use a slightly dodgy query to delete stuff...
      obj + ("delete" -> Json.obj("query" -> "id:\"%s\" OR %s:\"%s\"".format(id, ITEM_ID, id)))
    }
    solrUpdate(deleteJson, commit = commit)
  }

  /*
   * Delete a list of Solr models.
   */
  def deleteItems(items: Stream[Entity], commit: Boolean = true): Future[SolrResponse] = {
    deleteItemsById(items.map(_.id), commit = commit)
  }

  def deleteItemsByType(entityType: EntityType.Value, commit: Boolean = true): Future[SolrResponse] = {
    val deleteJson = Json.obj(
      "delete" -> Json.obj("query" -> (TYPE + ":" + entityType.toString))
    )
    solrUpdate(deleteJson, commit = commit)
  }

  /**
   * Delete everything in the index. USE WITH CAUTION!!!
   * @return
   */
  def deleteAll(commit: Boolean = false): Future[SolrResponse] = {
    val deleteJson = Json.obj(
      "delete" -> Json.obj("query" -> ("id:*"))
    )
    solrUpdate(deleteJson, commit = commit)
  }

  /*
   * Update a list of Solr models. The actual list is broken up
   * into batches of a fixed size so this function can accept
   * arbitrarily long lists.
   */
  def updateItems(items: Stream[Entity], commit: Boolean = true): Future[List[SolrResponse]] = {
    Future.sequence(items.grouped(batchSize).toList.map { batch =>
      try {
        solrUpdate(Json.toJson(batch.toList.flatMap(itemToJson)), commit = commit)
      } catch {
        case e: Throwable => {
          Logger.logger.error(e.getMessage, e)
          throw e
        }
      }
    })
  }

  /**
   * Run a Solr command, consisting of some Json.
   * @param updateJson
   * @return
   */
  private def solrUpdate(updateJson: JsValue, commit: Boolean = true): Future[SolrResponse] = {
    WS.url(updateUrl(commit)).withHeaders(headers.toList: _*)
        .post(updateJson).map { response =>
      response.json.validate[SolrUpdateResponse].fold({ err =>
          SolrErrorResponse(response.body)
        }, { sur => sur }
        )
    }
  }

  /**
   * Convert a given entity to the appropriate JSON for solr update.
   * @param item
   * @return
   */
  private def itemToJson(item: Entity): List[JsObject] = {
    item.isA match {
      case EntityType.DocumentaryUnit => docToSolr(DocumentaryUnit(item))
      case EntityType.HistoricalAgent => actorToSolr(HistoricalAgent(item))
      case EntityType.Repository => repoToSolr(Repository(item))
      case EntityType.Concept => conceptToSolr(Concept(item))
      case EntityType.Country => countryToSolr(Country(item))
      case any => entityToSolr(item)
    }
  }

  private def docToSolr(d: DocumentaryUnit): List[JsObject] = {
    val descriptions = describedEntityToSolr(d)
    descriptions.map { desc =>
      ((desc
        + ("copyrightStatus" -> Json.toJson(d.copyrightStatus))
        + ("scope" -> Json.toJson(d.scope))
        + ("parentId" -> Json.toJson(d.parent.map(_.id)))
        + ("depthOfDescription" -> Json.toJson(d.ancestors.length))
        + ("holderId" -> Json.toJson(d.holder.map(_.id))))
        + ("holderName" -> Json.toJson(d.holder.map(_.toString))))
    }
  }

  private def actorToSolr(d: HistoricalAgent): List[JsObject] = {
    val descriptions = describedEntityToSolr(d)
    // FIXME: This is very stupid
    descriptions.zipWithIndex.map { case (desc,i) =>
      ((desc
        + ("holderId" -> Json.toJson(d.set.map(_.id)))
        + ("holderName" -> Json.toJson(d.set.map(_.toString)))
        + (Isaar.ENTITY_TYPE -> Json.toJson(d.descriptions(i).stringProperty(Isaar.ENTITY_TYPE)))))
    }
  }

  private def repoToSolr(d: Repository): List[JsObject] = {
    val descriptions = describedEntityToSolr(d)
    descriptions.map { desc =>
      ((desc
        + ("addresses" -> Json.toJson(d.descriptions.flatMap(_.addresses.flatMap(_.formableOpt)).map(_.toString).distinct))
        + (OTHER_NAMES -> Json.toJson(d.descriptions.flatMap(_.listProperty(Isdiah.OTHER_FORMS_OF_NAME)).distinct))
        + (PARALLEL_NAMES -> Json.toJson(d.descriptions.flatMap(_.listProperty(Isdiah.PARALLEL_FORMS_OF_NAME)).distinct))
        + ("countryCode" -> Json.toJson(d.country.map(_.id)))
        + ("priority" -> Json.toJson(d.priority))))
    }
  }

  private def countryToSolr(d: Country): List[JsObject] = {
    val docs = entityToSolr(d.e)
    docs.map { desc =>
      // Ugh, do a deepMerge here so we overwrite any existing (null?) name value...
      (desc.deepMerge(Json.obj("name" -> Json.toJson(views.Helpers.countryCodeToName(d.id)(new Lang("en"))))))
    }
  }

  /**
   * Convert a Concept to JSON
   * @param c
   * @return
   */
  private def conceptToSolr(c: Concept): List[JsObject] = {
    val descriptions = describedEntityToSolr(c)
    descriptions.map { desc =>
      ((desc
        + ("parentId" -> Json.toJson(c.broaderTerms.map(_.id)))
        + ("holderId" -> Json.toJson(c.vocabulary.map(_.id))))
        + ("holderName" -> Json.toJson(c.vocabulary.map(_.toString))))
    }
  }

  /**
   * Convert a DescribedEntity into one flat Solr representation
   * per description.
   * @param d
   * @return
   */
  private def describedEntityToSolr[D <: Description](d: DescribedEntity[D]): List[JsObject] = {
    d.descriptions.map { desc =>
      val baseData = Json.obj(
        ITEM_ID -> d.id,
        "id" -> desc.id,
        "identifier" -> d.stringProperty("identifier"),
        NAME_EXACT -> desc.stringProperty("name"), // All descriptions should have a 'name' property
        TYPE -> d.isA,
        ACCESSOR_FIELD -> getAccessorValues(d.e),
        "lastUpdated" -> d.latestEvent.map(_.dateTime),
        "languageCode" -> desc.stringProperty(IsadG.LANG_CODE)
      )
      // Merge in all the additional data already in the entity
      // Don't overwrite keys added specifically
      desc.e.data.toSeq.foldLeft(baseData) { case (bd, (key, jsval)) =>
        if (!bd.keys.contains(key))
          bd + (dynamicFieldName(key, jsval), jsval)
        else
          bd
      }
    }
  }

  /**
   * Convert an AccessibleEntity to a Solr representation.
   * @param d
   * @return
   */
  private def entityToSolr(d: Entity): List[JsObject] = {
    val baseData = Json.obj(
      "id" -> d.id,
      ITEM_ID -> d.id, // Duplicate, because the 'description' IS the item.
      TYPE -> d.isA,
      NAME_EXACT -> d.stringProperty("name"),
      ACCESSOR_FIELD -> getAccessorValues(d),
      LAST_MODIFIED -> d.relations(AccessibleEntity.EVENT_REL).map(a => SystemEvent(a).dateTime)
    )
    // Merge in all the additional data already in the entity
    // Don't overwrite keys added specifically
    val full = d.data.toSeq.foldLeft(baseData) { case (bd, (key, jsval)) =>
      if (!bd.keys.contains(key))
        bd + (dynamicFieldName(key, jsval), jsval)
      else
        bd
    }
    List(full)
  }

  private def dynamicFieldName(key: String, jsValue: JsValue): String = {
    jsValue match {
      // Ugh, this is temporary hopefully...
      case s: JsString if s == "languageCode" => key
      case v: JsArray => key + "_ss" // Multivalue string
      case v: JsNumber => key + "_i"  // integer ???
      case v: JsString => key + "_t"  // Text general
      case v: JsBoolean => key + "_b" // Boolean
      case _ => key
    }
  }

  /**
   * Because it is not possible to filter query by empty multivalued fields in Solr,
   * an item with no accessor restrictions uses the single value ALLUSERS.
   * @param d
   * @return
   */
  private def getAccessorValues(d: Entity) = {
    if (d.relations(AccessibleEntity.ACCESS_REL).isEmpty)
      List(ACCESSOR_ALL_PLACEHOLDER)
    else
      d.relations(AccessibleEntity.ACCESS_REL).map(a => a.id)
  }
}
