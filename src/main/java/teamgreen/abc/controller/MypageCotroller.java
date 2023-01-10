package teamgreen.abc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import teamgreen.abc.domain.User1;
import teamgreen.abc.service.BoardService;
import teamgreen.abc.service.UserService;

@Controller
public class MypageCotroller {
    @Autowired
    UserService userService;

    @Autowired
    private BoardService boardService;

    @RequestMapping("/myPage")
    public String myPage(Model model){


        // TODO: 이곳에, 로그인한 유저가 있으면 반환, 없으면 예외 발생
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails  user1 =(UserDetails) principal;
        String userName=user1.getUsername();
       // Page<Board1> list =boardService.board1List(pageable);

        User1 userDetail = userService.getUser(userName);
        //model.addAttribute("list",list);
        model.addAttribute("userData",userDetail);
       // System.out.println(userDetail);
        return "thymeleaf/mypage/mypage";
    }

    @RequestMapping("/myPage/updateUser")
    public String updateUser(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails  user1 =(UserDetails) principal;
        String userName=user1.getUsername();
        User1 userDetail = userService.getUser(userName);
        model.addAttribute("userData",userDetail);

        return "thymeleaf/mypage/updateuser";
    }

    @RequestMapping("/myPage/updateHelper")
    public String updateHelper(){
        return "thymeleaf/mypage/updatehelper";
    }

    @RequestMapping("/myPage/updateParents")
    public String updateParents(){
        return "thymeleaf/mypage/updateparents";
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
