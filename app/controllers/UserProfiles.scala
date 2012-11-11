package controllers

import models.{UserProfile}
import models.base.AccessibleEntity
import defines._
import play.api.libs.concurrent.execution.defaultContext
import rest.EntityDAO
import controllers.base.CRUD
import models.UserProfileRepr


object UserProfiles extends AccessorController[UserProfile,UserProfileRepr] with CRUD[UserProfile,UserProfileRepr] {
  val entityType = EntityType.UserProfile
  val listAction = routes.UserProfiles.list _
  val createAction = routes.UserProfiles.createPost
  val updateAction = routes.UserProfiles.updatePost _
  val cancelAction = routes.UserProfiles.get _
  val deleteAction = routes.UserProfiles.deletePost _
  val permsAction = routes.UserProfiles.permissions _
  val setPermsAction = routes.UserProfiles.permissionsPost _
  val form = forms.UserProfileForm.form
  val showAction = routes.UserProfiles.get _
  val formView = views.html.userProfile.edit.apply _
  val showView = views.html.userProfile.show.apply _
  val listView = views.html.list.apply _
  val deleteView = views.html.delete.apply _
  val permView = views.html.permissions.edit.apply _
  val builder = UserProfileRepr
}
