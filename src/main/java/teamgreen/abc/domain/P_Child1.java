package teamgreen.abc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class P_Child1 {

    @Id
    private String p_id;

    @NotBlank(message = "아이의 이름을 입력하세요")
    private String p_childname;

    @NotBlank(message = "아이의 성별을 체크하세요")
    private String p_childgender;

    @NotNull(message = "아이의 만나이를 입력하세요")
    private Long p_childage;

    @NotBlank(message = "아이 소개를 입력하세요")
    private String p_childcomment;


}
