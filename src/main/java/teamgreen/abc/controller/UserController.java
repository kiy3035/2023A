package teamgreen.abc.controller;

import teamgreen.abc.domain.User1;
import teamgreen.abc.service.CheckUseridValidator;
import teamgreen.abc.repository.UserRepository;
import teamgreen.abc.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@Valid
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final CheckUseridValidator checkUseridValidator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;


    @GetMapping("/loginForm")
    public String loginForm() {
        return "thymeleaf/login";
    }


    /* 커스텀 유효성 검증을 위해 추가 */
    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(checkUseridValidator);

    }

    /** 회원가입 **/
    @GetMapping ("/signUp")
    public String get_signUp(User1 user1) {
        return "thymeleaf/signup";
    }

    @PostMapping ("/signUp")
    public String signUp(@Valid User1 user1, Errors errors, Model model)throws IOException {
        log.info("회원가입 실패");
        if(errors.hasErrors()){
            // 회원가입 실패시, 입력 데이터를 유지
            model.addAttribute("user1", user1);

            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));

            }

            // 회원가입 페이지로 다시 리턴
            return "thymeleaf/signup";
        }

        String rawPassword = user1.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user1.setPassword(encPassword);      // 비밀번호 암호화
        userRepository.save(user1);


        // 회원가입 성공시 alert 창 띄우기
        model.addAttribute("message", "회원가입이 완료되었습니다");
        model.addAttribute("searchUrl", "http://localhost:8080/loginForm");
        log.info("회원가입 성공");

        return "thymeleaf/message";

    }

    // 비번 찾기
    @RequestMapping("/findPW")
    public String findPW() {
        return "findpw";
    }


    // 아이디 찾기
    @RequestMapping("/findID")
    public String findID() {
        return "findId";
    }


    // 비번 변경폼
    @RequestMapping("/changePwForm")
    public String changePwForm(@RequestParam Map<String, Object> param, Model model) {
        model.addAttribute("userid", param.get("userid").toString());
        return "changePw";
    }


    // 아이디 찾기
    @PostMapping("/searchId")
    @ResponseBody
    public Map<String, Object> searchId(@RequestBody Map<String, Object> param) {
        Map<String, Object> map = new HashMap<>();
        String userid = userRepository.findUserId(param.get("username").toString(), param.get("email").toString());

        if (userid == null || userid.equals(""))
            map.put("result_userid", "0");
        else
            map.put("result_userid", userid);

        return map;
    }


    // 비번 확인
    @PostMapping("/checkPw")
    @ResponseBody
    public Map<String, Object> checkPw(@RequestBody Map<String, Object> param) {
        System.out.println(param);
        Map<String, Object> map = new HashMap<>();
        String checkPw = userRepository.findUserPw(param.get("userid").toString(), param.get("username").toString(), param.get("useremail").toString());
        System.out.println("checkPw=" + checkPw);

        if (checkPw == null || checkPw.equals(""))
            map.put("result", "0");
        else
            map.put("result", checkPw);

        return map;
    }


    // 비번 변경
    @PostMapping("/changePw")
    @ResponseBody
    @Transactional
    public Map<String, Object> changePw(@RequestBody Map<String, Object> param) {
        Map<String, Object> map = new HashMap<>();
        String encPassword = bCryptPasswordEncoder.encode(param.get("password").toString());
        userRepository.ChangePw(param.get("userid").toString(),encPassword);
        map.put("result", "success");
        return map;
    }



}



