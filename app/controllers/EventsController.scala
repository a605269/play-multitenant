package controllers

import models.{Event, Events, User}
import play.api.libs.json.Json
import play.api.mvc.BodyParsers.parse
import play.api.mvc.Controller

/**
  * Created by a605269 on 29-12-2016.
  */
class EventsController(eventService: Events) extends Controller {

  def push = TenantAction(parse.json) { request =>
    Json.fromJson[Event](request.body).fold(
      invalid => BadRequest,
      event => {
        val eventCreated = eventService.create(event)
        Created.withHeaders(LOCATION -> s"/events/${eventCreated.id}")
      }
    )
  }


  def get(id: Long) = TenantAction { request =>
    eventService.find(id.toString) match {
      case None => NotFound
      case Some(event) => Ok(Json.toJson(event))
    }
  }

}
