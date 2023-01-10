package teamgreen.abc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamgreen.abc.domain.Review1;

@Repository
public interface ReviewRepository extends JpaRepository <Review1, Long> {
    Page<Review1> findByReviewtitleContaining(String searchKeyword, Pageable pageable);
}
