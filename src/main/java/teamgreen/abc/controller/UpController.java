package teamgreen.abc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import teamgreen.abc.config.PrincipalDetails;
import teamgreen.abc.domain.Up;
import teamgreen.abc.repository.UpRepository;
import teamgreen.abc.service.UpService;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UpController {

    private final UpRepository upRepository;
    private final UpService upService;


    // 게시글 작성
    @GetMapping("/up/write")
    public String upWriteForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        model.addAttribute("id", principalDetails.getUsername());
        System.out.println("등업게시판 모델: " + model);

        return "thymeleaf/up/upwrite";
    }


    // 게시글 작성
    @PostMapping("/up/writeprocess")
    public String upgWriteProcess(Up up, Errors errors, Model model) throws Exception {

        if (errors.hasErrors()) {
            // 글 작성 실패시, 입력 데이터를 유지
            model.addAttribute("Up", up);
            log.info("등업 게시글 작성 실패");
        }

        upRepository.save(up);


        // 글 작성 성공시 alert
        model.addAttribute("message", "등업 신청 완료");
        model.addAttribute("searchUrl", "/up/list/");
        log.info("등업 글쓰기 성공");

        return "thymeleaf/commuting/commutingmessage";
    }


    // 게시글 목록
    @GetMapping("/up/list")
    public String upList(@PageableDefault(page = 0, size = 3, sort = "idx", direction = Sort.Direction.DESC) Pageable pageable,
                         Model model,Principal principal, Up up) {


        Page<Up> list = upRepository.findAllDesc(pageable);

        String userid = principal.getName();
        System.out.println("사탕:" + userid);

        up.setUserid(userid);
        System.out.println("드간것은?" + up.getUserid());

        upService.notice(userid);
        String notice = upService.notice(userid);

        if (notice.equals("1")){
            System.out.println("마 ㅡㅡ");
            model.addAttribute("fail", 1);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("userid", up.getUserid());
        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        System.out.println("등업 게시글 리스트 모델 : " + model);
        return "thymeleaf/up/uplist";
    }



}
