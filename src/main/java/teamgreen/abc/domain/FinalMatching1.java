package teamgreen.abc.domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@SequenceGenerator(
        name = "FinalMATCHING_SEQ_GENERATOR",
        sequenceName = "FinalMatching1_SEQ",
        initialValue = 1, allocationSize = 1
)
public class FinalMatching1 {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "FinalMATCHING_SEQ_GENERATOR")
    private Long midx;

    private String myid;

    private String opponentid;
}
