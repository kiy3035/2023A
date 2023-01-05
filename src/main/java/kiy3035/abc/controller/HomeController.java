package kiy3035.abc.controller;

import kiy3035.abc.domain.User1;
import kiy3035.abc.repository.UserRepository;
import kiy3035.abc.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/")
    public String home(Principal principal, Model model) {

        String userid = principal.getName();
        model.addAttribute("userid", userid);

        log.info("로그인한 아이디: " + model);
        return "thymeleaf/home";
    }

}

