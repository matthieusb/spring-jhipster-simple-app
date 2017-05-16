package test.aperture.gatling

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._
import scala.language.postfixOps

/**
  * This class is meant to load test main get routes of this app
  * The app has to be launched first (Manually, unfortunately)
  */
class MainSimulation extends Simulation {

    //    val app: ConfigurableApplicationContext = SpringApplication.run(classOf[SpringBootApertureTestingConfiguration])
    //
    //    Runtime.getRuntime.addShutdownHook(new Thread() {
    //        override def run(): Unit = app.stop()
    //    })

    val baseUrl: String = "localhost"
    val port: String = System.getProperty("server.port", "8080")

    val httpConf: HttpProtocolBuilder = http
        .baseURL(s"http://$baseUrl:$port") // Here is the root for all relative URLs

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
    setUp(scenario_main_get.inject(rampUsers(10000) over (5 seconds)).protocols(httpConf))
}
