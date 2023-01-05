package kiy3035.abc.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User1 {

    @Id
    @Column(name = "USER_ID")
    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,20}$", message = "아이디는 영어 소문자와 숫자만 사용하여 4~20자리여야 합니다.")
    private String userid;

    @Column(name = "USER_PW")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다")
//    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
//            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    @Column(name = "USER_NAME")
    @NotBlank(message = "이름은 필수 입력 값입니다")
    private String username;

    @Column(name = "USER_EMAIL")
    @Email
    @NotBlank(message = "이메일은 필수 입력 값입니다")
    private String useremail;

    @NotNull(message = "나이는 필수 입력 값입니다")    // NotBlank 입력시 오류
    private Long user_age;

    @NotBlank(message = "연락처는 필수 입력 값입니다")
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "예시) 01012345678")
    private String user_tel;


    private String user_comment;
    private String user_profile;
    private String user_kakao;
    private String user_role;


}
