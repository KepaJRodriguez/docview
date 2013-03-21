package models.forms

import play.api.data.Form
import play.api.data.Forms._
import models.{AnnotationF, AnnotationType, Entity}

/**
 * User: michaelb
 */
object AnnotationForm {

  import AnnotationF._

  val form = Form(mapping(
    Entity.ID -> optional(nonEmptyText),
    ANNOTATION_TYPE -> models.forms.enum(AnnotationType),
    BODY -> nonEmptyText, // TODO: Validate this server side
    FIELD -> optional(nonEmptyText),
    COMMENT -> optional(nonEmptyText)
  )(AnnotationF.apply)(AnnotationF.unapply))



  val multiForm = Form(    single(
    "annotation" -> list(tuple(
      "src" -> nonEmptyText,
      "dst" -> nonEmptyText,
      "data" -> form.mapping
    ))
  ))
}