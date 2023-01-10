package teamgreen.abc.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity // 이 클래스가 DB에 있는 테이블이라는 의미
@Data
@SequenceGenerator(
        name = "BOARD_SEQ_GENERATOR",
        sequenceName = "BOARD1_SEQ",    // 매핑할 DB 시퀀스 이름
        initialValue = 1, allocationSize = 1
)
public class Board1 {

    @Id // primary key를 의미
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "BOARD_SEQ_GENERATOR")
    @Column(name = "POST_IDX")
    private Long postidx;

    @Column(name = "POST_WRITER")
    private String postwriter;

    @Column(name = "POST_TITLE")
    private String posttitle;

    @Column(name = "POST_CONT")
    private String postcont;

    @Column(name = "POST_MENUIDX")
    private String postmenuidx;

    @Column(name = "POST_REGDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postregdate;

    // @Temporal(TemporalType.DATE)
    /*
    TemporalType.Date : 년-월-일 의 date 타입
    TemporalType.Time : 시:분:초 의 time 타입
    TemporalType.TIMESTAMP : timestamp(datetime) 타입
    */

    @Column(name = "POST_FILENAME")
    private String postfilename;

    @Column(name = "POST_FILEPATH")
    private String postfilepath;
}
