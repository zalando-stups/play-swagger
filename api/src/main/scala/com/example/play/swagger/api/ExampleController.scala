package com.example.play.swagger.api

import play.api.mvc.{Results, Action}

/**
 * This just exists for the example swagger compiler generated routes file,
 * but this project may define helpers, traits etc that the generated code
 * may use.
 */
class ExampleController {
  def index = Action {
    Results.Ok
  }
}
