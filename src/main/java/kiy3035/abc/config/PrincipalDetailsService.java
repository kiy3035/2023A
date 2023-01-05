package kiy3035.abc.config;

import kiy3035.abc.domain.User1;
import kiy3035.abc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
//@Transactional
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        System.out.println("userid:" + userid);
        //login 이 호출되면 자동 실행되어 userid가 DB에 있는지 확인한다.
        User1 principal = userRepository.findByUserid(userid);

        System.out.println("principal:" + principal);
        if (principal == null) {
            return null;
        } else {
            return new PrincipalDetails(principal);   // 비밀번호 검증을 알아서한다
        }
    }



}