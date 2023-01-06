package kiy3035.abc.controller;

import kiy3035.abc.domain.Helper1;
import kiy3035.abc.domain.User1;
import kiy3035.abc.repository.HelperRepository;
import kiy3035.abc.repository.ParentsRepository;
import kiy3035.abc.repository.UserRepository;
import kiy3035.abc.service.HelperService;
import kiy3035.abc.service.ParentsService;
import kiy3035.abc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final HelperRepository helperRepository;
    private final UserRepository userRepository;
    private final ParentsRepository parentsRepository;
    private final UserService userService;
    private final HelperService helperService;
    private final ParentsService parentsService;


    // 회원 목록 조회 + 검색
    @GetMapping("/userList")
    public String search(@RequestParam Map<String, Object> param, Model model,
                         @PageableDefault(page = 0, size = 3, sort = "userid", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<User1> list = null;

        if (param.get("userid") == null || param.get("userid").equals("")) {
            list = userService.findAllAsc(pageable);
//            model.addAttribute("users", userService.findAllAsc(pageable));

        } else {
            list = userRepository.findByUseridContaining(param.get("userid").toString(), pageable);
//            Page<User1> searchList  = userRepository.findByUseridContaining(param.get("userid").toString(), pageable);
//            model.addAttribute("users", searchList);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("users", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "thymeleaf/admin/userlist";
    }


    // 회원 상세 조회
    @GetMapping("/userList/{userid}")
    public String userDetail(@PathVariable("userid") String userid, Model model) {
        User1 userDetail = userService.getUser(userid);
        model.addAttribute("userDetail", userDetail);

        System.out.println("회원 상세 조회 =" + model);
        return "thymeleaf/admin/userdetail";
    }


    // 헬퍼 목록 조회
    @GetMapping("/helperList")
    public String helperList(Model model) {

        model.addAttribute("helpers", helperRepository.findHelperInfo());

        log.info("헬퍼 목록 조회" + model);

        return "thymeleaf/admin/helperlist";
    }


    // 헬퍼 상세 조회
    @GetMapping("/helperList/{helper_id}")
    public String helperDetail(@PathVariable("helper_id") String helper_id, Model model) {

        model.addAttribute("helperDetail", helperRepository.findHelperDetail());

        log.info("헬퍼 상세 조회" + model);

        return "thymeleaf/admin/helperdetail";
    }


    // 학부모 목록 조회
    @GetMapping("/parentsList")
    public String parentsList(Model model) {

        model.addAttribute("parents_s", parentsRepository.findParentsInfo());

        log.info("학부모 목록 조회" + model);

        return "thymeleaf/admin/parentslist";
    }


    // 학부모 상세 조회
    @GetMapping("/parentsList/{parents_id}")
    public String parentsDetail(@PathVariable("parents_id") String parents_id, Model model) {

        model.addAttribute("parentsDetail", parentsRepository.findParentsDetail());

        log.info("학부모 상세 조회" + model);

        return "thymeleaf/admin/parentsdetail";
    }



                        // 되는 로직  +  userdetail에 a href 와 관련
//    // 회원 삭제
//    @GetMapping("/deleteuser/{userid}")
//    public String deleteuser(@PathVariable String userid) {
//        userService.deleteuser(userid);
//
//        System.out.println("삭제 된 아이디: " + userid);
//        log.info("삭제됨요");
//        return "redirect:/admin/userList";
//    }



                            //requestparam / body 차이점 인지하기//
//    // 회원 삭제
//    @PostMapping("/deleteuser")
//    public String deleteuser(@RequestParam String userid) {
//        userService.deleteuser(userid);
//
//        System.out.println("삭제 된 아이디: " + userid );
//        log.info("삭제됨요");
//        return "redirect:/admin/userList";
//// return "thymeleaf/admin/userlist"; 로 하니까 개같은 페이지뜸 ㅅㅂ
//    }

    @PostMapping("/modifyRole")
    @ResponseBody
    public Map<String,Object> ModifyRole(@RequestBody Map<String, Object> param) throws IOException {

        Map<String,Object>  map = new HashMap<>();
        log.info("등급 변경된 유저는 " + param);
        userService.ModifyRole(param.get("userid").toString(), param.get("role").toString());
        map.put("result","success");

        return map;
    }


    @PostMapping("/deleteUser")
    @ResponseBody // 자바 객체를 HTTP 응답 본문의 객체로 변
    public Map<String,Object> deleteUser(@RequestBody Map<String,Object> param) throws IOException {

        Map<String,Object>  map = new HashMap<>();
        log.info("삭제된 유저는 " + param);
        userService.deleteUser(param.get("userid").toString());
        map.put("result","success");

        return map;
    }




}
