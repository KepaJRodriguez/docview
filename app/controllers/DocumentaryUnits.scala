package controllers

import controllers.base.EntityController
import controllers.base.EntityDelete
import controllers.base.EntityRead
import controllers.base.EntityUpdate
import controllers.base.VisibilityController
import defines.EntityType
import models.DocumentaryUnit
import models.DocumentaryUnitRepr
import models.base.Persistable
import models.base.AccessibleEntity
import play.api.data.Form
import play.api.libs.concurrent.execution.defaultContext
import play.api.mvc.Call
import play.api.mvc.RequestHeader
import rest.EntityDAO
import controllers.base.DocumentaryUnitCreator


object DocumentaryUnits extends DocumentaryUnitCreator[DocumentaryUnit,DocumentaryUnitRepr]
			with VisibilityController[DocumentaryUnit,DocumentaryUnitRepr]
			with EntityRead[DocumentaryUnitRepr]
			with EntityUpdate[DocumentaryUnit,DocumentaryUnitRepr]
			with EntityDelete[DocumentaryUnitRepr] {
  
  val entityType = EntityType.DocumentaryUnit
  val listAction = routes.DocumentaryUnits.list _
  val cancelAction = routes.DocumentaryUnits.get _
  val deleteAction = routes.DocumentaryUnits.deletePost _
  val updateAction = routes.DocumentaryUnits.updatePost _

  val setVisibilityAction = routes.DocumentaryUnits.visibilityPost _
  val visibilityAction = routes.DocumentaryUnits.visibility _
  val visibilityView = views.html.visibility.apply _    
  
  val form = forms.DocumentaryUnitForm.form
  val docForm = forms.DocumentaryUnitForm.form
  val showAction = routes.DocumentaryUnits.get _
  val docShowAction = routes.DocumentaryUnits.get _
  val docCreateAction = routes.DocumentaryUnits.docCreatePost _
  val formView = views.html.documentaryUnit.edit.apply _
  val showView = views.html.documentaryUnit.show.apply _
  val listView = views.html.documentaryUnit.list.apply _
  val docFormView = views.html.documentaryUnit.create.apply _
  val deleteView = views.html.delete.apply _
  val builder = DocumentaryUnitRepr

  def timeline(page: Int, limit: Int) = userProfileAction { implicit maybeUser =>
    implicit request =>

    if (isAjaxRequest(request)) {
      AsyncRest {
        rest.EntityDAO(entityType, maybeUser.flatMap(_.profile))
          .page(math.max(page, 1), math.max(limit, 1)).map { itemOrErr =>
            itemOrErr.right.map { page => 
            
              import com.codahale.jerkson.Json.generate

              val items = page.list.map(builder(_)).filterNot(_.dates.isEmpty).map { doc =>
                Map(
                  "title" -> doc.name,
                  "start" -> doc.dates.map(_.to).headOption.map(_.startDate.getYear.toString).getOrElse(""),
                  "end" -> doc.dates.map(_.to).lastOption.flatMap(_.endDate).map(_.getYear.toString).getOrElse(""),
                  "description" ->
                  doc.descriptions.headOption.flatMap(_.stringProperty(DocumentaryUnit.SCOPE_CONTENT.id)).getOrElse("")
                )
              }

              Ok(generate(items)).as("application/json") 
            }
          }
      }
    } else {
      Ok(views.html.documentaryUnit.timeline(maybeUser, request))
    }
  }
  
/*  def publishPost(id: String) = userProfileAction { implicit maybeUser =>
    implicit request =>
      AsyncRest {
        EntityDAO(entityType, maybeUser.flatMap(_.profile))
          .get(id).map { itemOrErr =>
            itemOrErr.right.map { item =>
              val doc = builder(item)
              AsyncRest {
                EntityDAO(entityType, maybeUser.flatMap(_.profile))
                  .update(id, doc.to.copy(publicationStatus = Some(defines.PublicationStatus.Published)).toData).map { itemOrErr =>
                    itemOrErr.right.map { item =>
                      Redirect(docShowAction(item.identifier))
                    }
                  }
              }
            }
          }
      }
  }*/
}


