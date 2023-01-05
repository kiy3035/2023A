package kiy3035.abc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Parents1 {

    @Id
    private String parents_id;

    @NotBlank(message = "사는 곳을 입력하세요")
    private String parents_addr;

    @NotBlank(message = "한줄 소개를 입력하세요")
    private String parents_comment;

    @NotBlank(message = "카카오톡 오픈링크를 입력하세요")
    private String parents_kakao;

    private String parents_go;
    private String parents_come;

    private String parents_filepath;


}
