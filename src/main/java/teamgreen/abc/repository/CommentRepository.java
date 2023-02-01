package teamgreen.abc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teamgreen.abc.domain.Comment1;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment1, Long> {

    @Query("select c from Comment1 c where c.commutingidx =:commutingidx order by c.idx asc")
    List<Comment1> getComment(@Param("commutingidx") Long commutingidx);

    // 댓글 삭제
    @Transactional
    @Modifying
    @Query("delete from Comment1 c where c.idx = :idx")
    void delete_comment(@Param("idx") Long idx);


    // 댓글 수정
    @Transactional
    @Modifying
    @Query("update  Comment1 c set c.content = :content , c.modifiedDate =:modified_date where c.idx = :idx")
    void update_comment(@Param("idx") Long idx,
                        @Param("content") String content,
                        @Param("modified_date") String modified_date);

}
