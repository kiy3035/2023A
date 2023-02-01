package teamgreen.abc.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
@ToString
@SequenceGenerator(
        name = "UP_SEQ_GENERATOR",
        sequenceName = "UP_SEQ",
        initialValue = 1, allocationSize = 1)
public class Up {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "UP_SEQ_GENERATOR")
    private Long idx;

    private String userid;

    private String content;

    private String time;

}
