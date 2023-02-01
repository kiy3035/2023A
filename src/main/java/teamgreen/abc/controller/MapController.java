package teamgreen.abc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {

    @GetMapping("/map")
    public String map() {

        return "thymeleaf/map/map_find";
    }
}
