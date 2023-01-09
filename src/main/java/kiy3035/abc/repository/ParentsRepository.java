package kiy3035.abc.repository;

import kiy3035.abc.domain.Parents1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ParentsRepository extends JpaRepository<Parents1, String> {


    @Query("select p.parents_filepath as parents_filepath , p.parents_id as parents_id , u.username as username , u.user_role as user_role from User1 u, Parents1 p where u.userid = p.parents_id")
    List<Map<String, Object>> findParentsInfo();


    // 학부모 상세 조회
    @Query("select " +
            "p.parents_id as parents_id, " +
            "p.parents_addr as parents_addr, " +
            "p.parents_comment as parents_comment, " +
            "p.parents_kakao as parents_kakao, " +
            "p.parents_go as parents_go, " +
            "p.parents_come as parents_come, " +
            "u.username as username " +
            "from User1 u, Parents1 p " +
            "where u.userid = p.parents_id and p.parents_id = :parents_id")
    List<Map<String, Object>> findParentsDetail(@Param("parents_id") String parents_id);
}
