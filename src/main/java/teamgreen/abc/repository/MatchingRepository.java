package teamgreen.abc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teamgreen.abc.domain.FinalMatching1;
import teamgreen.abc.domain.Heart;
import teamgreen.abc.domain.Matching1;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MatchingRepository extends JpaRepository<Matching1, Long> {

    Optional<Matching1> findByMyidAndOpponentid(String myid, String opponentid);


    // 나에게 도착한 매칭
    @Query("select m from Matching1 m where m.opponentid =:opponentid")
    List<Matching1> findMatchingList(@Param("opponentid") String opponentid);


    // 매칭 거절
    @Transactional
    @Modifying
    @Query("delete from Matching1 m where m.midx = :midx")
    void refuseone(@Param("midx") Long midx);
}
