package teamgreen.abc.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@SequenceGenerator(
        name = "MESSAGE_SEQ_GENERATOR",
        sequenceName = "MESSAGE_SEQ",
        initialValue = 1, allocationSize = 1)
public class Message {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "MESSAGE_SEQ_GENERATOR")
    private Long idx;

    private String senderid;

    private String receiverid;

    private String title;

    private String content;

    private String time;

    private String read;


}
