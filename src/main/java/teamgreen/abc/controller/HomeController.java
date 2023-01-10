package teamgreen.abc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import teamgreen.abc.repository.UserRepository;
import teamgreen.abc.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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


    @RequestMapping("/search")
    public String search() {
        return "thymeleaf/search/search";
    }


    @RequestMapping("/t")
    public String t() {
        return "thymeleaf/mypage/test";
    }

}



