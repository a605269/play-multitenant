package models

import db.DbContext


/**
  * Created by a605269 on 28-12-2016.
  */

case class Tenant(id: String)

case class Device(id: String, secret: String, tenant_id: String)

case class Event(id: String, device_id: String)

class Tenants(val db: DbContext) {

  import db._

  val tenants = quote(query[Tenant].schema(_.entity("tenants")))

  def find(id: String) = run(tenants.filter(c => c.id == lift(id))).headOption

  def create(tenant: Tenant) = tenant.copy(id = run(tenants.insert(lift(tenant)).returning(_.id)))

  def delete(tenant: Tenant) = run(tenants.filter(_.id == lift(tenant.id)).delete)

  def update(tenant: Tenant) = run(tenants.filter(_.id == lift(tenant.id)).update(lift(tenant)))
}

class Devices(val db: DbContext) {

  import db._

  val devices = quote(query[Device].schema(_.entity("devices")))

  def find(id: String) = run(devices.filter(c => c.id == lift(id))).headOption

  def create(device: Device) = device.copy(id = run(devices.insert(lift(device)).returning(_.id)))

  def delete(device: Device) = run(devices.filter(_.id == lift(device.id)).delete)

  def update(device: Device) = run(devices.filter(_.id == lift(device.id)).update(lift(device)))
}

class Events(val db: DbContext) {

  import db._

  val events = quote(query[Event].schema(_.entity("events")))

  def find(id: String) = run(events.filter(c => c.id == lift(id))).headOption

  def create(event: Event) = event.copy(id = run(events.insert(lift(event)).returning(_.id)))

  def delete(event: Event) = run(events.filter(_.id == lift(event.id)).delete)

  def update(event: Event) = run(events.filter(_.id == lift(event.id)).update(lift(event)))
}

object Event {

  import play.api.libs.functional.syntax.toFunctionalBuilderOps
  import play.api.libs.json.Reads
  import play.api.libs.json.Writes
  import play.api.libs.json.__

  /**
    * Converting JSON to object.
    */
  implicit val EventDataFromJson: Reads[Event] = (
    (__ \ "id").read[String] ~
      (__ \ "device_id").read[String]) (Event.apply _)

  /**
    * Converting object to JSON.
    */
  implicit val EventDataToJson: Writes[Event] = (
    (__ \ "id").write[String] ~
      (__ \ "device_id").write[String]) ((event: Event) => (
    event.id,
    event.device_id))
}
