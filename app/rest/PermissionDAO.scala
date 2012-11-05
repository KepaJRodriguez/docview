package rest

import play.api.libs.concurrent.execution.defaultContext
import scala.concurrent.Future
import play.api.libs.ws.WS
import models.Entity
import models.UserProfile
import play.api.libs.concurrent.execution.defaultContext
import scala.concurrent.Future
import play.api.libs.ws.WS
import models.{Accessor, PermissionSet,Group}
import com.codahale.jerkson.Json

import defines._

case class PermissionDAO[T <: Accessor[Group]](val accessor: T) extends RestDAO {

  def requestUrl = "http://%s:%d/%s/permission".format(host, port, mount)

  private val headers: Map[String, String] = Map(
    "Content-Type" -> "application/json",
    "Authorization" -> accessor.identifier
  )
  
  def get: Future[Either[RestError, PermissionSet[T]]] = {
    WS.url(requestUrl).withHeaders(headers.toSeq: _*).get.map { response =>
        checkError(response).right.map(r => PermissionSet(accessor, r.json))
      }
  }

  def get(user: T): Future[Either[RestError, PermissionSet[T]]] = {
    WS.url("%s/%s/%s".format(requestUrl, user.isA, user.identifier))
    	.withHeaders(headers.toSeq: _*).get.map { response =>
        checkError(response).right.map(r => PermissionSet[T](user, r.json))
      }
  }
    
  def set(user: T, data: Map[String, List[String]]): Future[Either[RestError, PermissionSet[T]]] = {
    WS.url("%s/%s/%s".format(requestUrl, user.isA, user.identifier))
    	.withHeaders(headers.toSeq: _*).post(Json.generate(data)).map { response =>
        checkError(response).right.map(r => PermissionSet[T](user, r.json))
      }
  }  
}