package teamgreen.abc.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Helper1 {


    @Id
    @Column(name = "HELPER_ID")
    private String helperid;

    @NotBlank(message = "사는 곳을 입력하세요")
    private String helper_addr;

    @NotBlank(message = "한줄 소개를 입력하세요")
    private String helper_comment;

    @NotBlank(message = "카카오톡 오픈링크를 입력하세요")
    private String helper_kakao;

    private Long helper_reccount;   // 기본값 0

    private String helper_go;
    private String helper_come;

//  @NotNull(message = "프로필 사진을 등록하세요")
//  NotNull, NotBlank, NotEmpty 하면 오류뜸
    private String helper_filepath;




}
