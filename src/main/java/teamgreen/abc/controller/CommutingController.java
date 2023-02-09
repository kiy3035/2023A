package teamgreen.abc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import teamgreen.abc.config.PrincipalDetails;
import teamgreen.abc.domain.*;
import teamgreen.abc.repository.*;
import teamgreen.abc.service.CommutingService;
import teamgreen.abc.service.MatchingService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommutingController {

    private final CommutingService commutingService;
    private final MatchingService matchingService;
    private final CommutingRepository commutingRepository;
    private final HeartRepository heartRepository;
    private final CommentRepository commentRepository;
    private final MatchingRepository matchingRepository;
    private final MessageRepository messageRepository;
    LocalDateTime now = LocalDateTime.now();
    User1 user1;



    // 게시글 작성
    @GetMapping("/commuting/write")
    public String commutingWriteForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        model.addAttribute("id", principalDetails.getUsername());
        System.out.println("프린시팔 모델: " + model);

        return "thymeleaf/commuting/commutingwrite";
    }


    // 게시글 작성
    @PostMapping("/commuting/writeprocess")
    public String commutingWriteProcess(Commuting1 commuting1,
                                        Errors errors, Model model, @RequestParam MultipartFile file) throws Exception {

        if (errors.hasErrors()) {
            // 글 작성 실패시, 입력 데이터를 유지
            model.addAttribute("Commuting1", commuting1);

            log.info("글 작성 실패");
        }

        String Filepath = commutingService.uploadFile(file);
        commuting1.setComfilepath(Filepath);

        int index = Filepath.lastIndexOf("_");
        String fileName = Filepath.substring(index + 1);
        commuting1.setComfilename(fileName);

        String commenuidx = commuting1.getCommenuidx();

        commutingRepository.save(commuting1);


        // 글 작성 성공시 alert
        model.addAttribute("message", "글 작성이 완료되었습니다");
        model.addAttribute("searchUrl", "/commuting/list/" + commenuidx);
        log.info("등하원 글쓰기 성공");

        return "thymeleaf/commuting/commutingmessage";
    }


    // 게시글 목록 + 검색
    @GetMapping("/commuting/list/{commenuidx}")
    public String commutingList(Model model, Long comidx, @PathVariable("commenuidx") String commenuidx, @RequestParam(value="value", required=false) String param,
                                @PageableDefault(page = 0, size = 5) Pageable pageable,
                                String comtitle) {

        System.out.println("뭔가?" + commenuidx);
        System.out.println("param이 뭔가?" + param);


            Page<Commuting1> list = null;
            Optional<String> oP = Optional.ofNullable(param);



          if (comtitle == null || comtitle.equals("")) {                        // 젤 기본 리스트
            list = commutingService.commuting1List(commenuidx, pageable);
             System.out.println("1단계");

              if(!oP.isEmpty() && oP.get().contains("view")){                   // 조회순
                  System.out.println("마스크맨");
                  list = commutingService.commuting1ListByView(commenuidx, pageable);
                  System.out.println("ㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂ");
                  model.addAttribute("value", param);
              }

              else if(!oP.isEmpty() && oP.get().contains("like")){               // 좋아요 순
                  System.out.println("젤리맨");
                  list = commutingService.commuting1ListByLike(commenuidx, pageable);
                  System.out.println("ㅅㅅㅅㅅㅅㅅㅅㅅㅅ");
                  model.addAttribute("value", param);
              }

          }

          else {                                                                // 검색했을때 리스트
            list = commutingService.commuting1SearchList(commenuidx, comtitle, pageable);
             System.out.println("2단계");

          }




        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

//        model.addAttribute("value", param);
        model.addAttribute("commenuidx", commenuidx);
        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        System.out.println("게시글 리스트 모델 : " + model);
        return "thymeleaf/commuting/commutinglist";
    }



    // 게시글 상세 조회 (조회수 중복 방지)
    @GetMapping("/commuting/view/{comidx}")
    public String commutingView(@PathVariable(name = "comidx") Long comidx, Principal principal, Model model,
                                HttpServletRequest request, HttpServletResponse response, HttpSession session, Matching1 matching1,
                                String myid, String opponentid) {

        String userid = principal.getName();
        log.info("유저아이디 ~~~!" + userid);


        // 조회수
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("viewCount")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + comidx + "]")) {
                // 기존 쿠키가 있지만 해당 board 조회가 없을 때
                commutingService.comCount(comidx);
                oldCookie.setValue(oldCookie.getValue() + "_[" + comidx + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            // 기존 쿠키가 없을 때
            commutingService.comCount(comidx);
            Cookie newCookie = new Cookie("viewCount", "[" + comidx + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }

        commutingService.findLike(comidx, userid);

        int like = commutingService.findLike(comidx, userid);

        model.addAttribute("like", like);
        model.addAttribute("userid", userid);
        model.addAttribute("commuting1", commutingService.commutingView(comidx));


        System.out.println("게시글상세보기의모델 : " + model);
        return "thymeleaf/commuting/commutingview";

    }


    // 좋아요
    @PostMapping("/like")
    public @ResponseBody int like(Long comidx, String userid) {

        int result = commutingService.saveLike(comidx, userid);
        System.out.println("result :" + result);
        return result;
    }


    // 게시글 삭제
    @ResponseBody
    @PostMapping("/commuting/delete")
    public Map<String,Object> commutingDelete(@RequestBody Map<String,Object> param) throws IOException {

        System.out.println("파람맨:" + param);
        Map<String,Object>  map = new HashMap<>();


        commutingRepository.delete_commuting(Long.parseLong(param.get("comidx").toString()),
                                            param.get("commenuidx").toString());

        map.put("result", "success");

        return map;
    }


    // 게시글 수정
    @GetMapping("/commuting/modify/{comidx}")
    public String commutingModify(@PathVariable("comidx") Long comidx, Model model) {

        model.addAttribute("commuting1", commutingService.commutingView(comidx));
        return "thymeleaf/commuting/commutingmodify";
    }


    // 게시글 수정
    @PostMapping("/commuting/update/{comidx}")
    public String commutingUpdate(@PathVariable("comidx") Long comidx, Commuting1 commuting1, MultipartFile file, Model model) throws Exception {


        // 기존에 있던 글이 담겨서 넘어옴
        Commuting1 commutingTemp = commutingService.commutingView(comidx);

        String commenuidx = commutingTemp.getCommenuidx();

        // 새로 입력한 내용을 기존 내용위에 덮어쓰기
        commutingTemp.setComtitle(commuting1.getComtitle());
        commutingTemp.setComcont(commuting1.getComcont());

        model.addAttribute("message", "글 수정 완료");
        model.addAttribute("searchUrl", "/commuting/list/" + commenuidx);


        System.out.println("오우오우" + commenuidx);
        System.out.println("담긴것은--------------: " + commuting1);
        commutingService.write(commutingTemp, file);

        return "thymeleaf/commuting/commutingmessage";
    }


    // 파일 다운로드
    @GetMapping("/commuting/{comfilepath}")
    public ResponseEntity<InputStreamResource> fileDownloadApi(@PathVariable("comfilepath") String comfilepath,
                                                               Commuting1 commuting1, MultipartFile file) throws IOException {
        return commutingService.fileDownload(file, commuting1);
    }


    // 댓글 쓰기
    @PostMapping("/comment/write")
    private String insertComment(@RequestParam("comidx") Long comidx,
                                 @RequestParam("content") String content,
                                 Principal principal) throws Exception {

        String writer = principal.getName();

        Comment1 comment1 = new Comment1();
        comment1.setCommutingidx(comidx);
        comment1.setWriter(writer);
        comment1.setContent(content);
        commutingService.insertComment(comment1);

        String redirect_url = "redirect:/commuting/view/" + Integer.toString(Math.toIntExact(comidx));
        return redirect_url;
    }


    // 댓글 List
    @GetMapping("/getCommentList")
    @ResponseBody
    private List<Comment1> getCommentList(@RequestParam("comidx") Long commutingidx,
                                          Model model) throws Exception {

        Comment1 comment1 = new Comment1();
        comment1.setCommutingidx(commutingidx);

        System.out.println("new comment1 =" + comment1);

        String writer = comment1.getWriter();
        String content = comment1.getContent();

        System.out.println("??" + writer + content);
        return commutingService.getCommentList(commutingidx);
    }


    // 댓글 수정
    @ResponseBody
    @PostMapping("/updateComment")
    public Map<String,Object> updateComment(@RequestBody Map<String,Object> param) throws IOException {

        System.out.println("(수정)넘어온 param=" + param);
        Map<String,Object>  map = new HashMap<>();

        commentRepository.update_comment(Long.parseLong(param.get("idx").toString()),
                                         param.get("content").toString(),
                                         param.get("modified_date").toString() );

        map.put("result","success");

        return map;
    }


    // 댓글 삭제
    @ResponseBody
    @PostMapping("/deleteComment")
    public Map<String,Object> deleteComment(@RequestBody Map<String,Object> param) throws IOException {

        System.out.println("(삭제)넘어온 param=" + param);
        Map<String,Object>  map = new HashMap<>();

        commentRepository.delete_comment(Long.parseLong(param.get("idx").toString()));
        map.put("result","success");

        return map;
    }


}