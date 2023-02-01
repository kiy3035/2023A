package teamgreen.abc.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import teamgreen.abc.domain.FinalMatching1;
import teamgreen.abc.domain.Heart;
import teamgreen.abc.domain.Matching1;
import teamgreen.abc.repository.MatchingRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MatchingService {

    MatchingRepository matchingRepository;


    // 매칭 중복 검사
    public String findUser(String myid, String opponentid) {
        // 저장된 값이 없다면 0, 있다면 1

        Optional<Matching1> findUser = matchingRepository.findByMyidAndOpponentid(myid, opponentid);

        if (findUser.isEmpty()) {
            return "0";
        } else {
            return "1";
        }
    }


    // 매칭 list
    public List<Matching1> matchingList(String opponentid){

        System.out.println("꼬깔콘:" + opponentid);
        return matchingRepository.findMatchingList(opponentid);
    }




}
