package teamgreen.abc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamgreen.abc.domain.Board1;

@Repository
public interface BoardRepository extends JpaRepository <Board1, Long> {

    Page<Board1> findByPosttitleContaining(String searchKeyword, Pageable pageable);
}
