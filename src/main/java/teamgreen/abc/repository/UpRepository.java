package teamgreen.abc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teamgreen.abc.domain.Up;

public interface UpRepository extends JpaRepository<Up, Long> {


    // 게시판 리스트
    @Query("select u from Up u order by u.idx desc")
    Page<Up> findAllDesc(Pageable pageable);


    // 경고문
    @Query("select count(u.content) from Up u where u.userid =:userid")
    int notice(@Param("userid") String userid);


}
