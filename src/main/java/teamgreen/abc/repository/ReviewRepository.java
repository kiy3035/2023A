package teamgreen.abc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import teamgreen.abc.domain.Review1;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Repository
public interface ReviewRepository extends JpaRepository <Review1, Long> {


    Page<Review1> findByReviewtitleContaining(String searchKeyword, Pageable pageable);


    @Transactional
    @Query(value = "select review_title,review_idx,created_date "+
            " from Review1 "+
            " where (review_pidx=:loguserid and review_menuidx=2) or (review_hidx=:loguserid and review_menuidx=1)",nativeQuery = true)
    List<Map<String,Object>> myTextList(@Param("loguserid")String loguserid);



}
