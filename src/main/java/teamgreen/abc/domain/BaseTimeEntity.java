package teamgreen.abc.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// JPA Auditing : DB에 생성시간, 수정시간 넣을 수 있게 해줌
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(name = "CREATED_DATE")
    private String createdDate;

    @LastModifiedDate
    @Column(name = "MODIFIED_DATE")
    private String modifiedDate;

    // 밑에 2개는 날짜 출력 형식 바꾸는 것
    @PrePersist
    public void onPrePersist(){
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        this.modifiedDate = this.createdDate;
    }

    @PreUpdate
    public void onPreUpdate(){
        this.modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
}

/*
- @MappedSuperClass:
JPA Entity클래스들이 BaseTimeEntity를 상속할 때
입력한 필드변수가 칼럼으로 인식하도록 함.

- @EntityListeners(AuditingEntityListener.class)
BaseTimeEntity클래스에 Auditing 기능을 포함시킴.

- @CreatedDate
Entity가 생성되어 저장될 때 시간이 자동 저장됨.

- @LastModifiedDate
조회한 Entity의 값을 변경할 때 시간이 자동 저장됨.
 */
