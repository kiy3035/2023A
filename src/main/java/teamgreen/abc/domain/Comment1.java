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
        name = "COMMUTING_SEQ_GENERATOR",
        sequenceName = "COMMENT1_SEQ",
        initialValue = 1, allocationSize = 1)
public class Comment1 extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "COMMUTING_SEQ_GENERATOR")
    private Long idx;

    private Long commutingidx;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 댓글 내용

    private String writer;


}
