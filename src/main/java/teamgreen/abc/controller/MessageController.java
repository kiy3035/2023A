package teamgreen.abc.controller;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import teamgreen.abc.domain.Message;
import org.springframework.data.domain.Pageable;

import teamgreen.abc.repository.MessageRepository;
import teamgreen.abc.service.MessageService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageRepository messageRepository;
    private final MessageService messageService;


    // 쪽지 쓰기
    @ResponseBody
    @PostMapping("/message")
    public Map<String, Object> message(@RequestBody Map<String, Object> param, Message message) throws IOException {

        System.out.println("(쪽지)넘어온 param=" + param);
        Map<String, Object> map = new HashMap<>();

        message.setSenderid(param.get("senderid").toString());
        message.setReceiverid(param.get("receiverid").toString());
        message.setTitle(param.get("title").toString());
        message.setContent(param.get("content").toString());
        message.setTime(param.get("time").toString());
        message.setRead("1");

        messageRepository.save(message);

        map.put("result", "success");

        return map;
    }

    // 쪽지 상세보기
    @ResponseBody
    @PostMapping("/message/view")
    public Map<String,Object> messageView(@RequestBody Map<String,Object> param, Message message) throws IOException {

        System.out.println("메시지 상세 뷰 파람맨:" + param);
        Map<String,Object>  map = new HashMap<>();

        messageRepository.read(Long.parseLong(param.get("idx").toString()));

        map.put("result", "success");

        return map;
    }


    // 받은 쪽지함
    @GetMapping("/messageList/receive")
    public String receive(Model model, String title, String senderid, Long idx,
                          @PageableDefault(page = 0, size = 10, sort = "time", direction = Sort.Direction.DESC) Pageable pageable) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails user1 =(UserDetails) principal;

        Page<Message> messagelist = null;

        if (title == null || title.equals("")) {
            messagelist = messageService.receiveMessageList(user1.getUsername(), pageable);
            System.out.println("나띵:" + messagelist);
        } else {
            messagelist = messageService.search(user1.getUsername(), title, pageable);

            System.out.println("썸띵" + messagelist);
        }

        int nowPage = messagelist.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, messagelist.getTotalPages());

        model.addAttribute("message", messagelist);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        System.out.println("모우델:" + model);

        return "thymeleaf/message/receive";
    }


    // 보낸 쪽지함
    @GetMapping("/messageList/send")
    public String send(Model model, String title, String receiverid, Long idx,
                       @PageableDefault(page = 0, size = 10, sort = "time", direction = Sort.Direction.DESC) Pageable pageable) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails user1 =(UserDetails) principal;
        Page<Message> messagelist = null;

        if (title == null || title.equals("")) {
            messagelist = messageService.sendMessageList(user1.getUsername(), pageable);
            System.out.println("나띵22:" + messagelist);
        } else {
            messagelist = messageService.search2(user1.getUsername(), title, pageable);

            System.out.println("썸띵22:" + messagelist);
        }

        int nowPage = messagelist.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, messagelist.getTotalPages());

        model.addAttribute("message", messagelist);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        System.out.println("모우델:" + model);

        return "thymeleaf/message/send";
    }


    // 쪽지 삭제
    @ResponseBody
    @PostMapping("/message/delete")
    public Map<String,Object> messageDelete(@RequestBody Map<String,Object> param) throws IOException {

        System.out.println("메시지파람맨:" + param);
        Map<String,Object>  map = new HashMap<>();


        messageRepository.delete_message(Long.parseLong(param.get("idx").toString()));

        map.put("result", "success");

        return map;
    }
}
