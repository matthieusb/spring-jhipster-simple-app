package controllers;

import java.util.concurrent.atomic.AtomicLong;

import io.swagger.annotations.ApiOperation;
import model.Greeting;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/greeting")
public class GreetingsController {

  private static final String template = "Hello, %s";
  private final AtomicLong counter = new AtomicLong();

  @ApiOperation(value = "getGreeting", nickname = "getGreeting")
  @RequestMapping(method=RequestMethod.GET, produces = "application/json")
  public @ResponseBody
  Greeting sayHello(@RequestParam(value="name", required=false, defaultValue="Stranger") String name) {
    return new Greeting(counter.incrementAndGet(), String.format(template, name));
  }
}
