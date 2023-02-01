package teamgreen.abc.controller;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.web.bind.annotation.*;
import teamgreen.abc.domain.P_Child1;
import teamgreen.abc.domain.Parents1;
import teamgreen.abc.repository.P_ChildRepository;
import teamgreen.abc.repository.ParentsRepository;
import teamgreen.abc.repository.UserRepository;
import teamgreen.abc.service.CheckUseridValidator;
import teamgreen.abc.service.ParentsService;
import teamgreen.abc.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import org.json.JSONObject;
import org.json.JSONArray;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@Valid
@RequiredArgsConstructor
public class ParentsController {

    private final UserRepository userRepository;
    private final ParentsRepository parentsRepository;
    private final P_ChildRepository p_childRepository;
    private final CheckUseridValidator checkUseridValidator;
    private final UserService userService;
    private final ParentsService parentsService;


    /**
     * 학부모 회원가입
     **/
    @GetMapping("/manager/signUp/Parents")
    public String signUp_parents(Parents1 parents1, Principal principal, Model model) {

        String parentsid = principal.getName();
        model.addAttribute("parentsid", parentsid);
        parents1.setParentsid(parentsid);

        log.info("헬퍼로 등록할 아이디: " + model);
        log.info("헬퍼로 등록할 아이디2: " + principal);
        log.info("헬퍼로 등록할 아이디3: " + parents1);

        return "thymeleaf/signup/signup_parents";
    }


    @PostMapping("/manager/signUp/Parents")
    public String signUp(@Valid Parents1 parents1, Errors errors, Model model, @RequestParam MultipartFile file) throws IOException {


        if (errors.hasErrors()) {
            // 회원가입 실패시, 입력 데이터를 유지
            model.addAttribute("Parents1", parents1);

            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            log.info("학부모 등록 실패");
            // 회원가입 페이지로 다시 리턴
            return "thymeleaf/signup/signup_parents";
        }

        String imageFileName = parentsService.uploadFile(file);
        parents1.setParents_filepath(imageFileName);
        parentsRepository.save(parents1);

        // 회원가입 성공시 alert 창 띄우기
        model.addAttribute("message", "아이 등록 화면으로 넘어갑니다");
        model.addAttribute("searchUrl", "http://localhost:8080/manager/signUp/Parents/Child");
        log.info("학부모 등록 성공");

        return "thymeleaf/signup/message";
    }


    /**
     * 아이 등록
     **/
    @GetMapping("/manager/signUp/Parents/Child")
    public String signUp_child(P_Child1 p_child1, Principal principal, Model model) {

        String p_id = principal.getName();
        model.addAttribute("p_id", p_id);
        p_child1.setP_id(p_id);

        log.info("아이 등록할 아이디: " + model);
        log.info("아이 등록할 아이디2: " + principal);
        log.info("아이 등록할 아이디3: " + p_child1);

        return "thymeleaf/signup/signup_child";
    }

    @ResponseBody
    @PostMapping(value = "/manager/signUp/Parents/Child")
    public Map<String, Object> signUp_child2(@Valid P_Child1 p_child1,
                                Errors errors,
                                Principal principal, Model model,
                                @RequestBody List<Map<String,Object>> params) throws IOException, ParseException {

        System.out.println("그대이름 파람 파람 파람~~~" + params);

        Map<String,Object>  map = new HashMap<>();

        List<P_Child1> ListP_Child1 = new ArrayList<>();

        for (int i = 0; i < params.size(); i++) {
            System.out.println("paramsize = " + params.size());

            P_Child1 p_child = new P_Child1();

//            params.get(i).get("name").toString();
            System.out.println(i+1 + "번째 아이 정보 :" + params.get(i));

            String p_id = params.get(i).get("id").toString();
            p_child.setP_id(p_id);

            String p_childname = params.get(i).get("name").toString();
            p_child.setP_childname(p_childname);

            String p_childgender = params.get(i).get("gender").toString();
            p_child.setP_childgender(p_childgender);

            Long p_childage = Long.valueOf(params.get(i).get("age").toString());
            p_child.setP_childage(p_childage);

            String p_childcomment = params.get(i).get("comment").toString();
            p_child.setP_childcomment(p_childcomment);

            System.out.println("p_child는 !!!!!" + p_child);
            ListP_Child1.add(p_child);
        }
        System.out.println("ListP_Child1 는요" + ListP_Child1);
        p_childRepository.saveAll(ListP_Child1);


        if (errors.hasErrors()) {
            // 아이 등록 실패시, 학부모 아이디 유지
            String p_id = principal.getName();
            model.addAttribute("p_id", p_id);
            // 아이 등록 실패시, 입력 데이터를 유지
            model.addAttribute("P_Child1", p_child1);

            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            log.info("아이 등록 실패");
            // 회원가입 페이지로 다시 리턴
//            return "thymeleaf/signup/signup_child";
        }


//        // 회원가입 성공시 alert 창 띄우기
//        model.addAttribute("message", "아이 등록이 완료되었습니다");
//        model.addAttribute("searchUrl", "http://localhost:8080");
//        log.info("아이 등록 성공");
//
//        return "thymeleaf/signup/message";
        map.put("result","success");

        return map;
    }


}
