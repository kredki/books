package pl.jstk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;

import java.security.Principal;

@Controller
public class HomeController {

    private static final String INFO_TEXT = "Here You shall display information containing information about newly created TO";
    protected static final String WELCOME = "This is a welcome page";
    private static final String LOGIN = "This is a login page";

    @GetMapping(value = {"/", "/welcome"})
    public String welcome(Model model) {
        model.addAttribute(ModelConstants.MESSAGE, WELCOME);
        model.addAttribute(ModelConstants.INFO, INFO_TEXT);
        return ViewNames.WELCOME;
    }

    @GetMapping(value = "/403")
    public String accesssDenied(Principal user, Model model) {


        if (user != null) {
            model.addAttribute("msg", "Hi " + user.getName()
                    + ", you do not have permission to access this page!");
        } else {
            model.addAttribute("msg",
                    "You do not have permission to access this page!");
        }

        return ViewNames.PERMISSION_DENIED;

    }
}