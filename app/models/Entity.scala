package models

import base.AccessibleEntity
import defines._
import play.api.libs.json._


object Entity {

  val ID = "id"
  val IDENTIFIER = "identifier"
  val ISA = "isA"
  val DATA = "data"
  val TYPE = "type"
  val RELATIONSHIPS = "relationships"

  def fromString(s: String, t: EntityType.Value) = {
    new Entity(s, t, Map(IDENTIFIER -> JsString(s)), Map())
  }


  import play.api.libs.json.Writes.{map => _, list => _, _}
  import play.api.libs.json.Reads._
  import play.api.libs.json.util._
  import play.api.libs.functional.syntax._
  import defines.EnumUtils

  implicit val entityWrites: Writes[Entity] = (
    (__ \ Entity.ID).write[String] and
      (__ \ Entity.TYPE).write[EntityType.Type](EnumUtils.enumWrites) and
      (__ \ Entity.DATA).lazyWrite(mapWrites[JsValue]) and
      (__ \ Entity.RELATIONSHIPS).lazyWrite(
        mapWrites[List[Entity]])
  )(unlift(Entity.unapply))

  implicit val entityReads: Reads[Entity] = (
    (__ \ Entity.ID).read[String] and
      (__ \ Entity.TYPE).read[EntityType.Type](EnumUtils.enumReads(EntityType)) and
      (__ \ Entity.DATA).lazyRead(map[JsValue]) and
      (__ \ Entity.RELATIONSHIPS).lazyRead(
        map[List[Entity]](list(entityReads)))
  )(Entity.apply _)
}

case class Entity(
  id: String,
  `type`: EntityType.Value,
  data: Map[String, JsValue] = Map(),
  relationships: Map[String, List[Entity]] = Map()) {

  def property(name: String) = data.get(name)

  /**
   * Shortcut for fetching a Option[String] property.
   */
  def stringProperty(name: String): Option[String] = property(name).flatMap(_.asOpt[String])
  def listProperty(name: String): Option[List[String]] = property(name).flatMap(v => v.asOpt[List[String]] orElse v.asOpt[String].map(List(_)) )
  def relations(s: String): List[Entity] = relationships.getOrElse(s, List())
  def withProperty(name: String, value: JsValue) = copy(data=data + (name -> value))
  def withRelation(s: String, r: Entity) = {
    val list: List[Entity] = relationships.getOrElse(s, Nil)
    copy(relationships=relationships + (s -> (list ++ List(r))))
  }
  private val adminKeys = List(Entity.IDENTIFIER)
  def valueData: Map[String, JsValue] = {
    data.filterNot { case (k, v) => adminKeys.contains(k) }
  }

  lazy val isA: EntityType.Value = `type`
}