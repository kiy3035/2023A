package kiy3035.abc.service;

import kiy3035.abc.domain.User1;
import kiy3035.abc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckUseridValidator extends AbstractValidator<User1> {

    private final UserRepository userRepository;

    @Override
    protected void doValidate(User1 user1, Errors errors) {
        if (userRepository.existsByUserid(user1.getUserid())) {
            errors.rejectValue("userid", "아이디 중복 오류", "이미 사용중인 아이디 입니다.");
        }
    }
}
