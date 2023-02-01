package teamgreen.abc.repository;

import org.springframework.data.jpa.repository.Modifying;
import teamgreen.abc.domain.Parents1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface ParentsRepository extends JpaRepository<Parents1, String> {

    Parents1 findByParentsid(String parentsid);


    @Query("select p.parents_filepath as parents_filepath , p.parentsid as parentsid , u.username as username , u.user_role as user_role from User1 u, Parents1 p where u.userid = p.parentsid")
    List<Map<String, Object>> findParentsInfo();


    // 학부모 상세 조회
    @Query("select distinct " +
            "p.parentsid as parentsid, " +
            "p.parents_addr as parents_addr, " +
            "p.parents_comment as parents_comment, " +
            "p.parents_kakao as parents_kakao, " +
            "p.parents_go as parents_go, " +
            "p.parents_come as parents_come, " +
            "u.username as username, " +
            "(select count(p_childname) from P_Child1 pc where pc.p_id = p.parentsid) as childcount " +
            "from User1 u, Parents1 p " +
            "where u.userid = p.parentsid and p.parentsid = :parentsid")
    List<Map<String, Object>> findParentsDetail(@Param("parentsid") String parentsid);


    @Modifying
    @Transactional
    @Query(" update Parents1 set parents_addr = :parents_addr,"+
            " parents_comment =:parents_comment,"+
            " parents_go =:parents_go,"+
            " parents_come =:parents_come,"+
            " parents_filepath =:parents_filepath"+
            " where parentsid =:parentsid")
    void updateParents(@Param("parentsid")String parentsid,@Param("parents_addr")String parents_addr,@Param("parents_comment")String parents_comment,
                       @Param("parents_go")String parents_go,@Param("parents_come")String parents_come,@Param("parents_filepath")String parents_filepath);



}
