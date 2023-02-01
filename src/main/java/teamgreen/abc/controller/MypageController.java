package teamgreen.abc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import teamgreen.abc.domain.Helper1;
import teamgreen.abc.domain.Parents1;
import teamgreen.abc.domain.User1;
import teamgreen.abc.repository.HelperRepository;
import teamgreen.abc.repository.ParentsRepository;
import teamgreen.abc.repository.ReviewRepository;
import teamgreen.abc.repository.UserRepository;
import teamgreen.abc.service.HelperService;
import teamgreen.abc.service.ParentsService;
import teamgreen.abc.service.ReviewService;
import teamgreen.abc.service.UserService;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MypageController {
    private final UserRepository userRepository;
    private final HelperRepository helperRepository;
    private final ParentsRepository parentsRepository;
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private  final HelperService helperService;
    private final ParentsService parentsService;
    private  final ReviewService reviewService;

    @RequestMapping("/myPage")
    public String myPage(Model model){


        // TODO: 이곳에, 로그인한 유저가 있으면 반환, 없으면 예외 발생
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails  user1 =(UserDetails) principal;
        String logUserId=user1.getUsername();
        User1 userDetail = userService.getUser(logUserId);
        Helper1 helperDetail = helperService.getHelper(logUserId);
        Parents1 parentsDetail = parentsService.getParents(logUserId);

        model.addAttribute("myTextList",reviewRepository.myTextList(logUserId));
        model.addAttribute("parentsData", parentsRepository.findParentsDetail(logUserId));
        model.addAttribute("helperData", helperRepository.findHelperDetail(logUserId));
        model.addAttribute("parentsDetail", parentsDetail);
        model.addAttribute("helperDetail", helperDetail);
        model.addAttribute("userData",userDetail);

        System.out.println(logUserId);
        System.out.println("내글 목록"+reviewRepository.myTextList(logUserId));
        System.out.println("헬퍼디테일"+helperDetail);
        System.out.println("학부모디테일"+ parentsDetail);
        System.out.println("유저디테일"+userDetail);

        return "thymeleaf/mypage/mypage";
    }

    // 유저정보수정
    @RequestMapping("/myPage/updateUser")
    public String updateUser(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails  user1 =(UserDetails) principal;
        String userName=user1.getUsername();
        User1 userDetail = userService.getUser(userName);
        model.addAttribute("userData",userDetail);
        System.out.println("하잉"+userDetail);

        return "thymeleaf/mypage/updateuser";
    }



    @RequestMapping("/myPage/userDataUpdate")
    public String userDataUpdate(User1 user1, Errors errors, Model model) throws IOException {
        System.out.println("유저다앗1!"+user1);
        userRepository.updateUser(user1.getUserid(),user1.getUsername(),user1.getUseremail(),
                user1.getUser_age(),user1.getUser_tel());
        System.out.println("유저다앗2!"+user1);


        return "thymeleaf/mypage";
    }





    // 헬퍼정보수정
    @RequestMapping("/myPage/updateHelper")
    public String updateHelper(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails  user1 =(UserDetails) principal;
        String logUserId=user1.getUsername();
        Helper1 helper1= helperService.getHelper(logUserId);
        model.addAttribute("helperData", helper1);
        System.out.println("제에에에발 헤엘퍼"+helper1);

        return "thymeleaf/mypage/updatehelper";
    }

    @RequestMapping("/myPage/helperDataUpdate")
    public String helperDataUpdate(Helper1 helper1, Errors errors, Model model,Object object) throws IOException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails  user1 =(UserDetails) principal;
        String logUserId=user1.getUsername();
        Helper1 helperDetail= helperService.getHelper(logUserId);
        helperRepository.updateHelper(helper1.getHelperid(), helper1.getHelper_addr(), helper1.getHelper_comment(),
                helper1.getHelper_go(), helper1.getHelper_come(), helper1.getHelper_filepath());
        System.out.println("헬헬헬1"+helper1);
        return "thymeleaf/mypage";
    }


    @RequestMapping("/myPage/updateParents")
    public String updateParents(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails  user1 =(UserDetails) principal;
        String logUserId=user1.getUsername();
        Parents1 parentsDetail= parentsService.getParents(logUserId);
        model.addAttribute("parentsData", parentsDetail);
        System.out.println("제에에에발 하아아악부모"+parentsDetail);

        return "thymeleaf/mypage/updateparents";
    }


    @RequestMapping("/myPage/parentsDataUpdate")
    public String parentsDataUpdate(Parents1 parents1, Errors errors, Model model) throws IOException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails  user1 =(UserDetails) principal;
        String logUserId=user1.getUsername();
        Parents1 parentsDetail= parentsService.getParents(logUserId);
        parentsRepository.updateParents(parents1.getParentsid(), parents1.getParents_addr(), parents1.getParents_comment(),
                parents1.getParents_go(), parents1.getParents_come(), parents1.getParents_filepath());
        System.out.println("페페페"+parents1);
        return "thymeleaf/mypage";
    }




    @RequestMapping("/myPage/reviewPage")
    public String reviewPage(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails  user1 =(UserDetails) principal;
        String userName=user1.getUsername();
        User1 userDetail = userService.getUser(userName);
        model.addAttribute("userData",userDetail);



        return "thymeleaf/review/reviewpage";
    }




}
