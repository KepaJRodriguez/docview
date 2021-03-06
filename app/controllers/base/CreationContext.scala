package controllers.base

import play.api.mvc._
import play.api.data.Form
import play.api.libs.concurrent.Execution.Implicits._
import defines.ContentType
import models.base.{Formable, Persistable, AccessibleEntity}
import defines.PermissionType
import models.{Entity, UserProfile}
import models.forms.VisibilityForm
import rest.EntityDAO

/**
 * Controller trait for extending Entity classes which server as
 * context for the creation of DocumentaryUnits, i.e. Repository and
 * DocumentaryUnit itself.
 */
trait CreationContext[CF <: Persistable, T <: AccessibleEntity] extends EntityRead[T] {

  def childCreateAction(id: String, ct: ContentType.Value)(f: Entity => Seq[(String,String)] => Seq[(String,String)] => Option[UserProfile] => Request[AnyContent] => Result) = {
    withItemPermission(id, PermissionType.Create, contentType, Some(ct)) { item => implicit userOpt => implicit request =>
      getUsersAndGroups { users => groups =>
        f(item)(users)(groups)(userOpt)(request)
      }
    }
  }

  def childCreatePostAction[CT<:Persistable](id: String, form: Form[CT], ct: ContentType.Value)(
        f: Entity => Either[(Form[CT],Form[List[String]]),Entity] => Option[UserProfile] => Request[AnyContent] => Result) = {
    withItemPermission(id, PermissionType.Create, contentType, Some(ct)) { item => implicit userOpt => implicit request =>
      form.bindFromRequest.fold(
        errorForm => f(item)(Left((errorForm,VisibilityForm.form)))(userOpt)(request),
        citem => {
          AsyncRest {
            val accessors = VisibilityForm.form.bindFromRequest.value.getOrElse(Nil)
            rest.EntityDAO(entityType, userOpt)
              .createInContext(id, ct, citem, accessors, logMsg = getLogMessage).map { itemOrErr =>
            // If we have an error, check if it's a validation error.
            // If so, we need to merge those errors back into the form
            // and redisplay it...
              if (itemOrErr.isLeft) {
                itemOrErr.left.get match {
                  case err: rest.ValidationError => {
                    val serverErrors = citem.errorsToForm(err.errorSet)
                    val filledForm = form.fill(citem).copy(errors = form.errors ++ serverErrors)
                    Right(f(item)(Left((filledForm,VisibilityForm.form)))(userOpt)(request))
                  }
                  case e => Left(e)
                }
              } else itemOrErr.right.map {
                citem => f(item)(Right(citem))(userOpt)(request)
              }
            }
          }
        }
      )
    }
  }
}
