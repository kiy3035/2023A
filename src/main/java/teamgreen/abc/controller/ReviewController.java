package teamgreen.abc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import teamgreen.abc.domain.Review1;
import teamgreen.abc.service.ReviewService;
import teamgreen.abc.service.UserService;

@Controller
public class ReviewController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;
//    private BoardService boardService;


    // 리뷰 작성 1 - 학부모가 헬퍼를 리뷰
    @GetMapping("/review/helper")
    public String reviewHelper(){

        return "thymeleaf/review/review_helper";
    }

    @PostMapping("/review/helperreviewprocess")
    public String reviewHelperReviewProcess(Review1 review1, Model model) throws Exception {

        reviewService.writeReview(review1);

        model.addAttribute("message", "리뷰 작성이 완료되었습니다");
        model.addAttribute("searchUrl", "/review/list");

        System.out.println("리뷰글 번호 : " + review1.getReviewidx());
        System.out.println("제목 : " + review1.getReviewtitle());
        System.out.println("리뷰 작성자 : " + review1.getReviewpidx());
        System.out.println("리뷰 대상자 : " + review1.getReviewhidx());
        System.out.println("리뷰 내용 : " + review1.getReviewcont());
        System.out.println("게시판 분류 : " + review1.getReviewmenuidx());
        System.out.println("작성 시간 : " + review1.getReviewregdate());

        return "thymeleaf/review/review_message";
    }

    // 리뷰 작성 2 - 헬퍼가 학부모를 리뷰
    @GetMapping("/review/parents")
    public String reviewParents(){

        return "thymeleaf/review/review_parents";
    }



    // 리뷰 목록 (헬퍼 + 학부모 전부)
    @GetMapping("review/list")
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

        model.addAttribute("review1", reviewService.reviewView(reviewidx));
        return "thymeleaf/review/review_view";
    }

    // 리뷰글 삭제
    @GetMapping("review/delete")
    public String reviewDelete(Long reviewidx) {

        reviewService.reviewDelete(reviewidx);
        return "redirect:/review/list";
    }
}
