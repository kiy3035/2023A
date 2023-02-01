package teamgreen.abc.repository;

import org.springframework.data.jpa.repository.Modifying;
import teamgreen.abc.domain.Helper1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface HelperRepository extends JpaRepository<Helper1, String> {


    // 헬퍼 목록 조회
    @Query("select " +
            "h.helper_filepath as helper_filepath, " +
            "h.helperid as helperid , " +
            "u.username as username , " +
            "u.user_role as user_role " +
            "from User1 u, Helper1 h " +
            "where u.userid = h.helperid")
    List<Map<String, Object>> findHelperInfo();


    // 헬퍼 상세 조회
    @Query("select " +
            "h.helperid as helperid, " +
            "h.helper_addr as helper_addr, " +
            "h.helper_comment as helper_comment, " +
            "h.helper_kakao as helper_kakao, " +
            "h.helper_reccount as helper_reccount, " +
            "h.helper_go as helper_go, " +
            "h.helper_come as helper_come, " +
            "u.username as username " +
            "from User1 u, Helper1 h " +
            "where u.userid = h.helperid and h.helperid = :helperid")
    
    List<Map<String, Object>> findHelperDetail(@Param("helperid") String helperid);



    Helper1 findByHelperid(String helperid);


    @Modifying
    @Transactional
    @Query(" update Helper1 set helper_addr = :helper_addr,"+
            " helper_comment =:helper_comment,"+
            " helper_go =:helper_go,"+
            " helper_come =:helper_come,"+
            " helper_filepath =:helper_filepath"+
            " where helperid =:helperid")
    void updateHelper(@Param("helperid")String helperid,@Param("helper_addr")String helper_addr,@Param("helper_comment")String helper_comment,
                      @Param("helper_go")String helper_go,@Param("helper_come")String helper_come,@Param("helper_filepath")String helper_filepath);
}
