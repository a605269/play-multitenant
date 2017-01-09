package models

import pdi.jwt.{JwtAlgorithm, JwtJson}
import play.api.libs.json.Json
import play.api.mvc.Results

import scala.util.{Failure, Success, Try}


/**
  * Created by a605269 on 03-01-2017.
  */
case class TenantClaim(tenant_id: String, stationCode: String, window: String)

object TenantClaim {

  import play.api.libs.functional.syntax.toFunctionalBuilderOps
  import play.api.libs.json.Reads
  import play.api.libs.json.Writes
  import play.api.libs.json.__

  /**
    * Converting JSON to object.
    */
  implicit val toTenantClaim : Reads[TenantClaim] = (
    (__ \ "tenant_id").read[String] ~
      (__ \ "stationCode").read[String] ~
      (__ \ "window").read[String]) (TenantClaim.apply _)

  /**
    * Converting object to JSON.
    */
  implicit val toJson : Writes[TenantClaim] = (
    (__ \ "tenant_id").write[String] ~
      (__ \ "stationCode").write[String] ~
      (__ \ "window").write[String]) ((claim: TenantClaim) => (
    claim.tenant_id,
    claim.stationCode,
    claim.window))
}

class TenantKey(token: String, secret: String) {

  private val algo = JwtAlgorithm.HS256
  private val jsonObj = JwtJson.decodeJson(token, secret, Seq(algo))

  def validate(): Boolean = jsonObj.isSuccess

  def claim: Try[TenantClaim] = {
    jsonObj match {
      case Success(obj) => {
       val tenantClaim = Json.fromJson[TenantClaim](jsonObj.get)
        println(tenantClaim)
       Success(tenantClaim.get)
    }
      case Failure(e) =>{
        Failure(e)
      }
    }
  }

  override def toString: String = ""
}
