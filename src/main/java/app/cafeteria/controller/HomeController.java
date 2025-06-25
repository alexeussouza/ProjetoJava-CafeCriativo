package app.cafeteria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/home")
    public ModelAndView home(Principal principal) {
        String usuario = principal.getName();
        ModelAndView mv = new ModelAndView("home"); // Aponta para home.html
        mv.addObject("usuario", usuario);
        return mv;
    }
}


