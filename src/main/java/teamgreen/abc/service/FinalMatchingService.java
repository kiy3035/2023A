package teamgreen.abc.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamgreen.abc.domain.FinalMatching1;
import teamgreen.abc.domain.Matching1;
import teamgreen.abc.domain.User1;
import teamgreen.abc.repository.FinalMatchingRepository;
import teamgreen.abc.repository.MatchingRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FinalMatchingService {

    private final FinalMatchingRepository finalMatchingRepository;


    // 매칭 중복 검사
    public String findUser(String myid, String opponentid) {
        // 저장된 값이 없다면 0, 있다면 1

        Optional<FinalMatching1> findUser = finalMatchingRepository.findByMyidAndOpponentid(myid, opponentid);

        if (findUser.isEmpty()) {
            return "0";
        } else {
            return "1";
        }
    }


    // 최종 매칭된 상대방 아이디 찾기
    public List<String> finalMatching1 (String myid) {


        System.out.println("염소");
        return finalMatchingRepository.findmatchedpeople(myid);
    }



}
