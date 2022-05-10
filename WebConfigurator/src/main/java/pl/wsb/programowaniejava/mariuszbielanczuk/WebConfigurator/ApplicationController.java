package pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {

    @GetMapping("")
    public String showHomePage(){
        return "index";
    }
}
