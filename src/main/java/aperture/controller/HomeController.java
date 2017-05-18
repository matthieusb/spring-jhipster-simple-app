package aperture.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for main home routes.
 */
@Controller
@EnableAutoConfiguration
public class HomeController {

    /**
     * Route redirecting to swagger.
     *
     * @return redirection vers swagger-ui
     */
    @RequestMapping("/")
    public String home() {
        return "redirect:swagger-ui.html";
    }
}
