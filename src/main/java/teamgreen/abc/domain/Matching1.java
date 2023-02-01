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
        name = "MATCHING_SEQ_GENERATOR",
        sequenceName = "Matching1_SEQ",
        initialValue = 1, allocationSize = 1
)
public class Matching1 {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "MATCHING_SEQ_GENERATOR")
    private Long midx;

    private String myid;

    private String opponentid;

}
