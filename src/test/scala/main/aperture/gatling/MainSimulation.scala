package scala.main.aperture.gatling

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._
import scala.language.postfixOps

class MainSimulation extends Simulation {
    val httpConf: HttpProtocolBuilder = http
        .baseURL("http://localhost:8080") // Here is the root for all relative URLs

    val scn: ScenarioBuilder = scenario("Sample")
        .exec(http("request1")
            .get("/"))
        .pause(100 milliseconds)
    setUp(scn.inject(rampUsers(20) over (5 seconds)).protocols(httpConf))
}
