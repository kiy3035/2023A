package teamgreen.abc.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
@ToString
@SequenceGenerator(
        name = "COMMUTING_SEQ_GENERATOR",
        sequenceName = "COMMUTING1_SEQ",
        initialValue = 1, allocationSize = 1)
public class Commuting1 extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "COMMUTING_SEQ_GENERATOR")
    @Column(name = "COM_IDX")
    private Long comidx;

    @Column(name = "COM_WRITER")
    private String comwriter;

    @Column(name = "COM_TITLE")
    private String comtitle;

    @Column(name = "COM_CONT")
    private String comcont;

    @Column(name = "COM_MENUIDX")
    private String commenuidx;

    @Column(name = "COM_FILEPATH")
    private String comfilepath;

    @Column(name = "COM_FILENAME")
    private String comfilename;

    @ColumnDefault("0")
    @Column(name = "COM_VIEW", nullable = false)
    private Long comview;

    private Long likecount;


}

