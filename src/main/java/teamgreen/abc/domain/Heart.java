package teamgreen.abc.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@ToString
@SequenceGenerator(
        name = "HEART_SEQ_GENERATOR",
        sequenceName = "HEART_SEQ",
        initialValue = 1, allocationSize = 1
)
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "HEART_SEQ_GENERATOR")
    private Long idx;


    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User1.class)
    @JoinColumn(name = "userid")
    private User1 user1;


    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Commuting1.class)
    @JoinColumn(name = "comidx")
    private Commuting1 commuting1;


    public static Heart toHeart(User1 user1, Commuting1 commuting1){

        Heart heart = new Heart();
        heart.setUser1(user1);
        heart.setCommuting1(commuting1);

        return heart;
    }

}
