package controllers

import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits._
import play.api.Play.current
import base.{Authorizer,AuthController,LoginHandler}
import play.api.data.Form
import play.api.data.Forms._
import play.api.Play
import java.io.{FilenameFilter, File}

/**
 * Controller for rendering end-user help documentation stored in
 * /docs
 */
object Help extends Controller with Authorizer with AuthController {

  val helpDir = "help" // Relative to root directory, alongside "public"

  /**
   * List available help pages.
   */
  def list = userProfileAction { implicit userOpt => implicit request =>
    Ok(views.html.helpIndex(listHelpPages))
  }

  /**
   * Render an individual help page.
   * @param page
   */
  def page(page: String) = userProfileAction { implicit userOpt => implicit request =>
    getHelpText(page).map { text =>
      Ok(views.html.help(page, text))
    }.getOrElse(NotFound(views.html.errors.itemNotFound()))
  }

  private def getHelpText(page: String): Option[String] = {
    val file = new File(new File(Play.application.path, helpDir), page + ".md")
    (file.exists && file.isFile) match {
      case true => Some(scala.io.Source.fromFile(file).mkString)
      case false => None
    }
  }

  private def listHelpPages: List[String] = {
    val path = new File(Play.application.path, helpDir)
    if (!path.exists()) Nil
    else {
      path.listFiles(new FilenameFilter {
        def accept(dir: File, name: String): Boolean = name.endsWith(".md")
      }).map { file =>
        val s = file.getAbsoluteFile.getName
        s.substring(0, s.length - 3)
      }.toList.sorted
    }
  }
}