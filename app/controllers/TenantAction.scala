package controllers

import models.TenantKey
import play.api.libs.json.Json
import play.api.mvc.{ActionBuilder, Request, Result, Results}

import scala.concurrent.Future

/**
  * Created by a605269 on 29-12-2016.
  */
object TenantAction extends ActionBuilder[Request] with Results with Logging {

  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]): Future[Result] = {
    val token = request.headers.get("tenant-key")
    //Todo : Secret to be retrieved from Database based on the device_id passed in
    val secret = "secret"
    token match {
      case Some(strToken) => {
        val tenantKey = new TenantKey(strToken, secret)
        println("Tenant Token ::: " + tenantKey.claim.get.toString)
        if (tenantKey.validate()) block(request) else Future.successful(Unauthorized(Json.obj("message" -> "Invalid Tenant Key")))
      }
      case None => {
        Future.successful(Unauthorized(Json.obj("message" -> "Tenant Key Not Found")))
      }
    }
  }


}


