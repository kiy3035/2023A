package teamgreen.abc.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity // 이 클래스가 DB에 있는 테이블이라는 의미
@Data
@SequenceGenerator(
        name = "REVIEW_SEQ_GENERATOR",
        sequenceName = "REVIEW1_SEQ",   // 매핑할 DB 시퀀스 이름
        initialValue = 1, allocationSize = 1
)
@Getter
@NoArgsConstructor
public class Review1 extends BaseTimeEntity {   // BaseTimeEntity를 상속함으로써 사용 가능!

    // 리뷰 글번호
    @Id // primary key를 의미
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "REVIEW_SEQ_GENERATOR")
    @Column(name = "REVIEW_IDX")
    private Long reviewidx;

    // 리뷰 - 헬퍼
    @Column(name = "REVIEW_HIDX")
    private String reviewhidx;

    // 리뷰 - 학부모
    @Column(name = "REVIEW_PIDX")
    private String reviewpidx;

    // 리뷰 제목
    @Column(name = "REVIEW_TITLE")
    private String reviewtitle;

    // 리뷰 내용
    @Column(name = "REVIEW_CONT")
    private String reviewcont;

    // 리뷰 작성일
    @Column(name = "REVIEW_REGDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reviewregdate;

    // 리뷰 추천수
    @Column(name = "REVIEW_RECOMMEND")
    private Long reviewrecommend;

    // 리뷰 메뉴번호
    @Column(name = "REVIEW_MENUIDX")
    private String reviewmenuidx;
}
