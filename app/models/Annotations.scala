package models

/**
 * This type alias provides a means to ensure that the
 * @Relation(type) annotation on constructor val params
 * is copied to the field value, as described here:
 *
 * http://www.scala-lang.org/api/current/index.html#scala.annotation.target.package
 *
 * See also:
 *
 * http://stackoverflow.com/questions/11853878/getannotations-on-scala-class-fields
 */
object Annotations {
  type Relation = models.Relation @scala.annotation.target.field
}

