package kiy3035.abc.controller;

import kiy3035.abc.domain.Parents1;
import kiy3035.abc.repository.ParentsRepository;
import kiy3035.abc.repository.UserRepository;
import kiy3035.abc.service.CheckUseridValidator;
import kiy3035.abc.service.ParentsService;
import kiy3035.abc.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Slf4j
@Controller
@Valid
@RequiredArgsConstructor
public class ParentsController {

    private final UserRepository userRepository;
    private final ParentsRepository parentsRepository;
    private final CheckUseridValidator checkUseridValidator;
    private final UserService userService;
    private final ParentsService parentsService;


    /**
     * 학부모 회원가입
     **/
    @GetMapping("/manager/signUp/Parents")
    public String signUp_parents(Parents1 parents1, Principal principal, Model model) {

        String parents_id = principal.getName();
        model.addAttribute("parents_id", parents_id);
        parents1.setParents_id(parents_id);

        log.info("헬퍼로 등록할 아이디: " + model);
        log.info("헬퍼로 등록할 아이디2: " + principal);
        log.info("헬퍼로 등록할 아이디3: " + parents1);

        return "thymeleaf/signup_parents";
    }


    @PostMapping("/manager/signUp/Parents")
    public String signUp(@Valid Parents1 parents1, Errors errors, Model model, @RequestParam MultipartFile file) throws IOException {
        log.info("학부모 등록 실패");
        if (errors.hasErrors()) {
            // 회원가입 실패시, 입력 데이터를 유지
            model.addAttribute("Parents1", parents1);

            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));

            }
            // 회원가입 페이지로 다시 리턴
            return "thymeleaf/signup_parents";
        }

        String imageFileName = parentsService.uploadFile(file);

        parents1.setParents_filepath(imageFileName);
        parentsRepository.save(parents1);

        // 회원가입 성공시 alert 창 띄우기
        model.addAttribute("message", "학부모 등록이 완료되었습니다");
        model.addAttribute("searchUrl", "http://localhost:8080");
        log.info("학부모 등록 성공");

        System.out.println("코멘트요_____" + parents1.getParents_comment());

        return "thymeleaf/message";
    }


}
