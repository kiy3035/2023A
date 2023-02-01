package teamgreen.abc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import teamgreen.abc.domain.FinalMatching1;
import teamgreen.abc.domain.Matching1;
import teamgreen.abc.domain.User1;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface FinalMatchingRepository extends JpaRepository<FinalMatching1, Long> {


    Optional<FinalMatching1> findByMyidAndOpponentid(String myid, String opponentid);


    // 나랑 최종 매칭된 사람 찾기
    @Query("select f.opponentid from FinalMatching1 f where f.myid =:myid")
    List<String> findmatchedpeople(@Param("myid") String myid);


    // 나랑 최종 매칭된 사람 찾기
    @Query("select f.myid from FinalMatching1 f where f.opponentid =:opponentid")
    List<String> findmatchedpeople2(@Param("opponentid") String opponentid);
}
