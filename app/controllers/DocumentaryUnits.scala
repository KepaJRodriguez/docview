package controllers

import controllers.base.EntityController
import controllers.base.EntityDelete
import controllers.base.EntityRead
import controllers.base.EntityUpdate
import controllers.base.VisibilityController
import defines.{ ContentType, EntityType }
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

object DocumentaryUnits extends DocumentaryUnitCreator[DocumentaryUnit, DocumentaryUnitRepr]
  with VisibilityController[DocumentaryUnit, DocumentaryUnitRepr]
  with EntityRead[DocumentaryUnitRepr]
  with EntityUpdate[DocumentaryUnit, DocumentaryUnitRepr]
  with EntityDelete[DocumentaryUnitRepr] {

  val entityType = EntityType.DocumentaryUnit
  val contentType = ContentType.DocumentaryUnit
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
}


