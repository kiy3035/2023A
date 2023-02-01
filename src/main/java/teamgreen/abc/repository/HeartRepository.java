package teamgreen.abc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teamgreen.abc.domain.Commuting1;
import teamgreen.abc.domain.Heart;
import teamgreen.abc.domain.User1;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;


public interface HeartRepository extends JpaRepository<Heart, Long> {


    Optional<Heart> findByCommuting1_ComidxAndUser1_Userid(Long comidx, String userid);

    void  deleteByCommuting1_ComidxAndUser1_Userid(Long comidx, String userid);
}
