package teamgreen.abc.service;

import teamgreen.abc.domain.User1;
import teamgreen.abc.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;


    // 회원가입 시 유효성 체크
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        // 유효성 및 중복 검사에 실패한 필드 목록을 받음
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }


    // 아이디 중복 여부 확인
    @Transactional
    public void checkUseridDuplication(User1 user1) {
        boolean useridDuplicate = userRepository.existsByUserid(user1.getUserid());
        if (useridDuplicate) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }


    // 회원 목록 조회 [관리자]
    @Transactional
    public Page<User1> findAllAsc(Pageable pageable) {
        return userRepository.findAllAsc(pageable);
    }


    // 회원 상세 조회 [관리자]
    @Transactional
    public User1 getUser(String userid){
        Optional<User1> userWrapper = Optional.ofNullable(userRepository.findByUserid(userid));
        if(userWrapper.isPresent())
        {
            User1 user1 = userWrapper.get();

            User1 userDetail = User1.builder()
                    .userid(user1.getUserid())
                    .username(user1.getUsername())
                    .useremail(user1.getUseremail())
                    .user_age(user1.getUser_age())
                    .user_tel(user1.getUser_tel())
                    .user_role(user1.getUser_role())
                    .build();

            return userDetail;
        }

        return null;
    }

    // 유저 삭제 [관리자]
    public void deleteUser(String userid){
        userRepository.deleteUser(userid);
    }


    // 유저 등급 변경 [관리자]
    public void ModifyRole(String userid, String role){
        userRepository.ModifyRole(userid, role);
    }


    // 회원 검색 기능
    @Transactional
    public Page<User1> search(String userid, Pageable pageable) {
        Page<User1> userList = userRepository.findByUseridContaining(userid, pageable);
        return userList;
    }



}
