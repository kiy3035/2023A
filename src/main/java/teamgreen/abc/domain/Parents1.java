package teamgreen.abc.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Parents1 {


    @Id
    @Column(name = "PARENTS_ID")
    private String parentsid;

    @NotBlank(message = "사는 곳을 입력하세요")
    private String parents_addr;

    @NotBlank(message = "한줄 소개를 입력하세요")
    private String parents_comment;

    @NotBlank(message = "카카오톡 오픈링크를 입력하세요")
    private String parents_kakao;

    private String parents_go;
    private String parents_come;

//    @NotEmpty(message = "프로필 사진을 등록하세요")
    private String parents_filepath;


}
