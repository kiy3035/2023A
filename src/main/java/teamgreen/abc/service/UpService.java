package teamgreen.abc.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import teamgreen.abc.repository.UpRepository;


@Service
@AllArgsConstructor
public class UpService {

    UpRepository upRepository;


    public String notice(String userid) {

        int A = upRepository.notice(userid);

        if (A >= 3) {
            return "1";
        } else {
            return "0";
        }
    }

}
