package main.aperture.gatling

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._
import scala.language.postfixOps

class MainSimulation extends Simulation {
    val httpConf: HttpProtocolBuilder = http
        .baseURL("http://localhost:8080") // Here is the root for all relative URLs

    val scenario_main_get: ScenarioBuilder = scenario("Testing main get routes")
        .exec(http("Request on main route")
            .get("/"))
        .pause(100 milliseconds)
        .exec(http("Request on rooms main route")
            .get("/api/rooms"))
        .pause(100 milliseconds)
        .exec(http("Request on subjects main route")
            .get("/api/subjects"))
        .pause(100 milliseconds)
        .exec(http("Request on supervisors main route")
            .get("/api/supervisors"))
    setUp(scenario_main_get.inject(rampUsers(1000) over (5 seconds)).protocols(httpConf))
}
