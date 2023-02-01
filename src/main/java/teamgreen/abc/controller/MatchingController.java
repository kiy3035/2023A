package teamgreen.abc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import teamgreen.abc.config.PrincipalDetails;
import teamgreen.abc.domain.Comment1;
import teamgreen.abc.domain.FinalMatching1;
import teamgreen.abc.domain.Matching1;
import teamgreen.abc.repository.FinalMatchingRepository;
import teamgreen.abc.repository.MatchingRepository;
import teamgreen.abc.service.FinalMatchingService;
import teamgreen.abc.service.MatchingService;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Controller
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;
    private final MatchingRepository matchingRepository;
    private final FinalMatchingService finalMatchingService;
    private final FinalMatchingRepository finalMatchingRepository;


    // 매칭 신청
    @ResponseBody
    @PostMapping("/matching")
    public Map<String,Object> matching(@RequestBody Map<String,Object> param, Matching1 matching1, Model model) throws IOException {

        System.out.println("(매칭)넘어온 파람 = " + param);
        Map<String,Object>  map = new HashMap<>();

        matching1.setMyid(param.get("myid").toString());
        matching1.setOpponentid(param.get("opponentid").toString());

        String myid = param.get("myid").toString();
        String opponentid = param.get("opponentid").toString();

        matchingService.findUser(myid, opponentid);

        String A = matchingService.findUser(myid, opponentid);
        String B = finalMatchingService.findUser(myid, opponentid);

        model.addAttribute("ResultA", A);
        System.out.println("에이의 값은2222?" + A);
        System.out.println("비의 값은------" + B);


        if (A.equals("1")) {
            map.put("result", "fail");
            return map;
        } if (B.equals("1")) {
            map.put("result", "fail2");
            return map;
        }
        else {
            matchingRepository.save(matching1);
        }
        map.put("result","success");

        return map;
    }


    // 매칭 리스트
    @GetMapping("/matching")
    public String matchingList(Model model, Principal principal){

        System.out.println("ㅇㅇㅇㅇ??" + principal.getName());

        List<Matching1> matching1 = matchingService.matchingList(principal.getName());

        System.out.println("나열? " + matching1);


        model.addAttribute("matching1", matching1);
        System.out.println("매칭리스트 모델" + model);

        return "thymeleaf/matching/matchinglist";
    }


    // 매칭 수락
    @PostMapping("/matching/agree")
    @ResponseBody
    public Map<String,Object> agree(@RequestBody Map<String,Object> param, FinalMatching1 finalMatching1, String myid, String opponentid) throws IOException {

        Map<String,Object>  map = new HashMap<>();
        log.info("수락으로부터 넘어온 파람은? " + param);

        finalMatching1.setMyid(param.get("myid").toString());
        finalMatching1.setOpponentid(param.get("opponentid").toString());

        // 매칭 대기 테이블에서 지우고
        matchingRepository.refuseone(Long.parseLong(param.get("midx").toString()));
        // 찐 매칭된 테이블엔 값 저장
        finalMatchingRepository.save(finalMatching1);
        map.put("result","success");

        return map;
    }


    // 매칭 거절
    @PostMapping("/matching/refuse")
    @ResponseBody
    public Map<String,Object> refuse(@RequestBody Map<String,Object> param) throws IOException {

        Map<String,Object>  map = new HashMap<>();
        log.info("거절된 유저idx는? " + param);

        matchingRepository.refuseone(Long.parseLong(param.get("midx").toString()));
        map.put("result","success");

        return map;
    }


}
