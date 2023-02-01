package teamgreen.abc.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import teamgreen.abc.config.PrincipalDetails;
import teamgreen.abc.domain.User1;
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
    public String home(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {       // Principal principal 대신 @AuthenticationPrincipal PrincipalDetails principalDetails

        String userid    = principalDetails.getUsername();
        String user_role = principalDetails.getUser1().getUser_role();  // 컬렉션이라 이렇게 받아옴  // AuthenticationPrincipal : 세션 정보 UserDetails 에 접근할 수 있는 어노테이션

        System.out.println("principalDetails :" + principalDetails);

        model.addAttribute("userid", userid);
        model.addAttribute("user_role", user_role);

        log.info("로그인한 아이디에 담긴 값: " + model);
        return "thymeleaf/home";
    }



}



