package kiy3035.abc.repository;

import kiy3035.abc.domain.Helper1;
import kiy3035.abc.domain.User1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface HelperRepository extends JpaRepository<Helper1, String> {


    // 헬퍼 목록 조회
    @Query("select " +
            "h.helper_filepath as helper_filepath, " +
            "h.helper_id as helper_id , " +
            "u.username as username , " +
            "u.user_role as user_role " +
            "from User1 u, Helper1 h " +
            "where u.userid = h.helper_id")
    List<Map<String, Object>> findHelperInfo();


    // 헬퍼 상세 조회
    @Query("select " +
            "h.helper_id as helper_id, " +
            "h.helper_addr as helper_addr, " +
            "h.helper_comment as helper_comment, " +
            "h.helper_kakao as helper_kakao, " +
            "h.helper_reccount as helper_reccount, " +
            "h.helper_go as helper_go, " +
            "h.helper_come as helper_come, " +
            "u.username as username " +
            "from User1 u, Helper1 h " +
            "where u.userid = h.helper_id and h.helper_id = :helper_id")
    List<Map<String, Object>> findHelperDetail(@Param("helper_id") String helper_id);



}
