package kiy3035.abc.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Helper1 {


    @Id
    private String helper_id;

    @NotBlank(message = "사는 곳을 입력하세요")
    private String helper_location;

    @NotBlank(message = "한줄 소개를 입력하세요")
    private String helper_comment;

    @NotBlank(message = "카카오톡 오픈링크를 입력하세요")
    private String helper_kakao;

    private Long helper_reccount;

    private String helper_go;
    private String helper_come;

    private String helper_filepath;

    private String sample6_postcode;
    private String sample6_address;
    private String sample6_detailAddress;
    private String sample6_extraAddress;



}
