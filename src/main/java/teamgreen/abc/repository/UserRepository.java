package teamgreen.abc.repository;

import teamgreen.abc.domain.Commuting1;
import teamgreen.abc.domain.User1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

// CRUD 함수를 들고있음
// @Repository 어노테이션이 없어도 IOC가 된다
public interface UserRepository extends JpaRepository<User1, String>  {

   // select * from User1 where user_id = ?
   User1 findByUserid(String userid);


   @Query("select userid from User1 where username = :username and useremail = :useremail")
    String findUserId(@Param("username") String username, @Param("useremail") String useremail);


   @Query("select userid from User1 where userid = :userid and username = :username and useremail = :useremail")
    String findUserPw(@Param("userid") String userid, @Param("username") String username, @Param("useremail") String useremail);


   @Modifying
   @Query("update User1 set password = :password where userid = :userid")
    void ChangePw(@Param("userid") String userid, @Param("password") String password);


    @Modifying
    @Transactional
    @Query(" update User1 set user_age = :user_age,"+
            " user_name =:user_name,"+
            " useremail =:useremail,"+
            " user_tel =:user_tel"+
            " where userid =:userid")
    void updateUser(@Param("userid")String user_id,@Param("user_name")String user_name,@Param("useremail")String useremail,
                    @Param("user_age")Long user_age,@Param("user_tel")String user_tel);


    // 중복 검사
    boolean existsByUserid(String userid);


    // 모든 회원 조회 [관리자]
    @Query("select u from User1 u order by u.username asc")
    Page<User1> findAllAsc(Pageable pageable);


    // 회원 삭제 [관리자]
    @Transactional
    @Modifying
    @Query("delete from User1 u where u.userid = :userid")
    void deleteUser(@Param("userid") String userid);


    // 회원 등업 [관리자]
    @Modifying
    @Query("update User1 set user_role = :user_role where userid = :userid")
    void ModifyRole(@Param("userid") String userid , @Param("user_role") String user_role);


    // 회원 검색 [관리자]
    Page<User1> findByUseridContaining(@Param("userid") String userid, Pageable pageable);



}
