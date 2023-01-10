package teamgreen.abc.controller;

import teamgreen.abc.domain.Helper1;
import teamgreen.abc.repository.HelperRepository;
import teamgreen.abc.repository.UserRepository;
import teamgreen.abc.service.CheckUseridValidator;
import teamgreen.abc.service.HelperService;
import teamgreen.abc.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;


@Slf4j
@Controller
@Valid
@RequiredArgsConstructor
public class HelperController {

    private final UserRepository userRepository;
    private final HelperRepository helperRepository;
    private final CheckUseridValidator checkUseridValidator;
    private final UserService userService;
    private final HelperService helperService;


    /**
     * 헬퍼 회원가입
     **/
    @GetMapping("/manager/signUp/Helper")
    public String signUp_helper(Helper1 helper1, Principal principal, Model model) {

        String helper_id = principal.getName();
        model.addAttribute("helper_id", helper_id);
        helper1.setHelper_id(helper_id);

        log.info("헬퍼로 등록할 model아이디: " + model);
        log.info("헬퍼로 등록할 principal아이디: " + principal);
        log.info("헬퍼로 등록할 helper1아이디: " + helper1);

        return "thymeleaf/signup_helper";
    }


    @PostMapping("/manager/signUp/Helper")
    public String signUp(@Valid Helper1 helper1, Errors errors, Model model, @RequestParam MultipartFile file) throws IOException {

        log.info("헬퍼 등록 실패");

        if (errors.hasErrors()) {
            // 회원가입 실패시, 입력 데이터를 유지
            model.addAttribute("Helper1", helper1);

            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));

            }
            // 회원가입 페이지로 다시 리턴
            return "thymeleaf/signup_helper";
        }


        String imageFileName = helperService.uploadFile(file);

        helper1.setHelper_filepath(imageFileName);
        helperRepository.save(helper1);

        // 회원가입 성공시 alert 창 띄우기
        model.addAttribute("message", "헬퍼 등록이 완료되었습니다");
        model.addAttribute("searchUrl", "http://localhost:8080");
        log.info("헬퍼 등록 성공");



        return "thymeleaf/message";
    }





}
