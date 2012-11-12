package models

import defines._

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

/**
 * Base class for `pure` form-backed models that need to be
 * persisted on the server.
 */
trait Persistable {
  def id: Option[Long]
  def isA: EntityType.Value

  /**
   * Turn the item back into some raw data that can be
   * posted to the rest server.
   */
  def toData: Map[String, Any] = {

    (Map[String, Any]() /: getClass.getDeclaredFields) { (a, f) =>
      f.setAccessible(true)

      if (f.getName == "id") {
        a + (f.getName -> f.get(this))
      } else {
        // Handle relations...
        Option(f.getAnnotation(classOf[Relation])) match {
          case Some(rel) => {
            val relmap = a.getOrElse("relationships", Map[String, Any]()).asInstanceOf[Map[String, Any]]
            val rellst = f.get(this) match {
              case lst: List[_] => lst.asInstanceOf[List[Persistable]].map(_.toData)
              case sng: Persistable => List(sng).map(_.toData)
              case _ => Nil
            }
            a + ("relationships" -> (relmap + (rel.value -> rellst)))
          }
          case None => {
            val datamap: Map[String, Any] = a.getOrElse("data", Map()).asInstanceOf[Map[String, Any]]
            val value = f.get(this) match {
              case None => None
              case enum: Enumeration#Value => enum.toString
              case Some(value) => value match {
                case enum: Enumeration#Value => enum.toString
                case x => x
              }
              case x => x
            }
            a + ("data" -> (datamap + (f.getName -> value)))
          }
        }
      }
    }
  }
}