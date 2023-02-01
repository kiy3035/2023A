package teamgreen.abc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@SequenceGenerator(
        name = "CHILD_SEQ_GENERATOR",
        sequenceName = "P_CHILD1_SEQ",    // 매핑할 DB 시퀀스 이름
        initialValue = 1, allocationSize = 1
)
public class P_Child1 {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "CHILD_SEQ_GENERATOR")
    private Long p_childidx;

    private String p_id;    // 부모id 외래키

    @NotBlank(message = "아이의 이름을 입력하세요")
    private String p_childname;

    @NotBlank(message = "아이의 성별을 체크하세요")
    private String p_childgender;

    @NotNull(message = "아이의 만나이를 입력하세요")
    private Long p_childage;

    @NotBlank(message = "아이 소개를 입력하세요")
    private String p_childcomment;


}
