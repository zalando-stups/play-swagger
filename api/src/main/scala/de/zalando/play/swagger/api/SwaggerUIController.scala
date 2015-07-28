package de.zalando.play.swagger.api

import play.api.mvc.{Action, Results}

class SwaggerUIController {
  def index(file: String, dir: String, subDir: String) = Action {
    Results.Ok
  }

  def index(file: String, dir: String) = Action {
    Results.Ok
  }

  def index(file: String) = Action {
    Results.Ok
  }
}
