package guru.springframework.springrestclientexamples.controllers;

import guru.springframework.springrestclientexamples.numberwrapper.NumberWrapper;
import guru.springframework.springrestclientexamples.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"", "/", "index.html"})
    public String enterUserList() {
        return "index";
    }

    @PostMapping("/users")
    public String displayUsers(@ModelAttribute NumberWrapper limit, Model model) {
        log.info("############ Received Limit: {}", limit);
        model.addAttribute("users", userService.getUsers(limit.getLimit()));
        return "userlist";
    }

}
