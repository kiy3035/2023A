package teamgreen.abc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import teamgreen.abc.domain.Commuting1;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CommutingRepository extends JpaRepository <Commuting1, Long> {


    // 등하원 게시글 검색
    Page<Commuting1> findByCommenuidxAndComtitleContaining(String commenuidx,
                                              String comtitle, Pageable pageable);


    // 등하원 게시판 리스트
    @Query("select c from Commuting1 c where c.commenuidx =:commenuidx order by c.comidx desc")
    Page<Commuting1> findAllDesc(@Param("commenuidx") String commenuidx,
                                 Pageable pageable);


    // 조회순 나열
    @Query("select c from Commuting1 c where c.commenuidx =:commenuidx order by c.comview desc")
    Page<Commuting1> findAllByViewDesc(@Param("commenuidx") String commenuidx,
                                       Pageable pageable);


    // 좋아요순 나열
    @Query("select c from Commuting1 c where c.commenuidx =:commenuidx order by c.likecount desc")
    Page<Commuting1> findAllByLikeDesc(@Param("commenuidx") String commenuidx,
                                       Pageable pageable);


    // 게시글 삭제
    @Transactional
    @Modifying
    @Query("delete from Commuting1 c where c.comidx = :comidx and c.commenuidx =:commenuidx")
    void delete_commuting(@Param("comidx") Long comidx,
                          @Param("commenuidx") String commenuidx);


    // 조회수
    @Modifying
    @Transactional
    @Query("update Commuting1 c set c.comview = c.comview + 1 where c.comidx = :comidx")
    void comCount(@Param("comidx") Long comidx);


    // 좋아요 추가
    @Modifying
    @Transactional
    @Query(value = "update Commuting1 c set c.likecount = c.likecount + 1 where c.comidx = :comidx")
    int plusLike(@Param("comidx") Long comidx);


    // 좋아요 삭제
    @Modifying
    @Transactional
    @Query(value = "update Commuting1 c set c.likecount = c.likecount - 1 where c.comidx = :comidx")
    int minusLike(@Param("comidx") Long comidx);



}

