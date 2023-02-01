package teamgreen.abc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import teamgreen.abc.domain.FinalMatching1;
import teamgreen.abc.domain.Matching1;
import teamgreen.abc.domain.Review1;
import teamgreen.abc.domain.User1;
import teamgreen.abc.repository.FinalMatchingRepository;
import teamgreen.abc.repository.ReviewRepository;
import teamgreen.abc.service.FinalMatchingService;
import teamgreen.abc.service.ReviewService;
import teamgreen.abc.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    LocalDateTime now = LocalDateTime.now();
    // 현재시간 가져오는 것


    private final  UserService userService;

    private  final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private final FinalMatchingService finalMatchingService;
    private final FinalMatchingRepository finalMatchingRepository;
//    private BoardService boardService;


    // 리뷰 작성 1 - 학부모가 헬퍼를 리뷰
    // 밑 부분은 인영, 식이가 만들어 놓은 부분 그대로 참고
    @GetMapping("/review/helper")
    public String reviewHelper(Review1 review1, FinalMatching1 finalMatching1, Model model){

        // TODO: 이곳에, 로그인한 유저가 있으면 반환, 없으면 예외 발생
        Object principal  = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails user1 = (UserDetails) principal;
        String userName   = user1.getUsername();
        // Page<Board1> list =boardService.board1List(pageable);

        User1 userDetail = userService.getUser(userName);


        // 나랑 최종 매칭된 상대방 아이디 넘겨주기
        String userid = user1.getUsername();
        System.out.println("크크" + userid);

        review1.setReviewpidx(userid);

        finalMatching1.setMyid(userid);

      List<String> list  = finalMatchingService.finalMatching1(userid);
      List<String> list2 = finalMatchingRepository.findmatchedpeople2(userid);

        for(String data : list){
            System.out.println(data);
        }


        model.addAttribute("list",list);
        model.addAttribute("list2",list2);
        model.addAttribute("userData",userDetail);
        // System.out.println(userDetail);

        return "thymeleaf/review/review_helper";
    }


    @PostMapping("/review/helperreviewprocess")
    public String reviewHelperReviewProcess(Review1 review1, Model model) throws Exception {

        reviewService.writeReview(review1);

        model.addAttribute("message", "리뷰 작성이 완료되었습니다");
        model.addAttribute("searchUrl", "/review/list");

        System.out.println("리뷰글 번호 : " + review1.getReviewidx());
        System.out.println("제목 : " + review1.getReviewtitle());
        System.out.println("리뷰 작성자(학부모) : " + review1.getReviewpidx());
        System.out.println("리뷰 대상자(헬퍼) : " + review1.getReviewhidx());
        System.out.println("리뷰 내용 : " + review1.getReviewcont());
        System.out.println("게시판 분류 : " + review1.getReviewmenuidx());
        System.out.println("작성 시간 : " + review1.getCreatedDate());

        return "thymeleaf/review/review_message";
    }


    // 리뷰 작성 2 - 헬퍼가 학부모를 리뷰
    // 밑 부분은 인영, 식이가 만들어 놓은 부분 그대로 참고
    @GetMapping("/review/parents")
    public String reviewParents(Review1 review1, Model model, FinalMatching1 finalMatching1){

        // TODO: 이곳에, 로그인한 유저가 있으면 반환, 없으면 예외 발생
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails  user1 =(UserDetails) principal;
        String userName=user1.getUsername();
        // Page<Board1> list =boardService.board1List(pageable);

        User1 userDetail = userService.getUser(userName);
        //model.addAttribute("list",list);
        model.addAttribute("userData",userDetail);
        // System.out.println(userDetail);


        // 나랑 최종 매칭된 상대방 아이디 넘겨주기
        String userid = user1.getUsername();
        System.out.println("크크" + userid);

        review1.setReviewhidx(userid);
        System.out.println("크크2" + review1.getReviewhidx());

        finalMatching1.setMyid(userid);
        System.out.println("크크3" + finalMatching1.getMyid());

        List<String> list = finalMatchingService.finalMatching1(userid);
        List<String> list2 = finalMatchingRepository.findmatchedpeople2(userid);

        for(String data : list){
            System.out.println(data);
        }

        model.addAttribute("list",list);
        model.addAttribute("list2",list2);

        return "thymeleaf/review/review_parents";
    }


    @PostMapping("/review/parentsreviewprocess")
    public String reviewParentsReviewProcess(Review1 review1, Model model) throws Exception {

        reviewService.writeReview(review1);

        model.addAttribute("message", "리뷰 작성이 완료되었습니다");
        model.addAttribute("searchUrl", "/review/list");

        System.out.println("리뷰글 번호 : " + review1.getReviewidx());
        System.out.println("제목 : " + review1.getReviewtitle());
        System.out.println("리뷰 작성자(헬퍼) : " + review1.getReviewhidx());
        System.out.println("리뷰 대상자(학부모) : " + review1.getReviewpidx());
        System.out.println("리뷰 내용 : " + review1.getReviewcont());
        System.out.println("게시판 분류 : " + review1.getReviewmenuidx());
        System.out.println("작성 시간 : " + review1.getCreatedDate());

        return "thymeleaf/review/review_message";
    }

    // 리뷰 목록 (헬퍼 + 학부모 전부)
    @GetMapping("/review/list")
    public String reviewList(Model model,
                             @PageableDefault(page = 0, size = 10, sort = "reviewidx", direction = Sort.Direction.DESC) Pageable pageable,
                             String searchKeyword) {

        Page<Review1> list = null;

        if(searchKeyword == null) {
            list = reviewService.review1List(pageable);
        } else {
            list = reviewService.review1SearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "thymeleaf/review/review_list_css";
    }

    // 리뷰글 상세 페이지
    @GetMapping("/review/view")
    public String reviewView(Model model, Long reviewidx) {

        // TODO: 이곳에, 로그인한 유저가 있으면 반환, 없으면 예외 발생
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails  user1 =(UserDetails) principal;
        String userName=user1.getUsername();

        User1 userDetail = userService.getUser(userName);
        model.addAttribute("userData",userDetail);
        
        //------------ 위에는 userData 가져오기용 (principal로 대체 가능)
        //------------ 밑에는 상세 페이지
        
        model.addAttribute("review1", reviewService.reviewView(reviewidx));
        return "thymeleaf/review/review_view";
    }

    // 리뷰글 삭제
    @GetMapping("/review/delete")
    public String reviewDelete(Long reviewidx) {

        reviewService.reviewDelete(reviewidx);
        return "redirect:/review/list";
    }

    // 리뷰글 수정
    @GetMapping("/review/modify/{reviewidx}")
    public String reviewModify(@PathVariable("reviewidx") Long reviewidx,
                               Model model) {

        model.addAttribute("review1", reviewService.reviewView(reviewidx));
        return "thymeleaf/review/review_modify";
    }

    @PostMapping("/review/update/{reviewidx}")
    public String reviewUpdate(@PathVariable("reviewidx") Long reviewidx,
                               Review1 review1) throws Exception {

        // 기존 리뷰글이 담겨서 넘어옴
        Review1 reviewTemp = reviewService.reviewView(reviewidx);

        // 새로 입력한 리뷰 내용을 덮어쓰기 - 제목, 내용만 바꾸기로..
        reviewTemp.setReviewtitle(review1.getReviewtitle());
        reviewTemp.setReviewcont(review1.getReviewcont());

        reviewService.writeReview(reviewTemp);

        return "redirect:/review/list";
    }
}
