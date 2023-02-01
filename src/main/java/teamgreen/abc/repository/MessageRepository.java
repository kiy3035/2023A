package teamgreen.abc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teamgreen.abc.domain.Message;

import javax.transaction.Transactional;


public interface MessageRepository extends JpaRepository<Message, Long> {

    // 받은 쪽지함
    @Query("select m from Message m where m.receiverid =:receiverid")
    Page<Message> receiveMessageList(@Param("receiverid") String receiverid,
                                     Pageable pageable);


    // 쪽지 검색 [받은 쪽지함]
    Page<Message> findByReceiveridAndTitleContaining(@Param("receiverid") String receiverid,
                                                     @Param("title") String title, Pageable pageable);


    // 보낸 쪽지함
    @Query("select m from Message m where m.senderid =:senderid")
    Page<Message> sendMessageList(@Param("senderid") String senderid,
                                     Pageable pageable);

    // 쪽지 검색 [보낸 쪽지함]
    Page<Message> findBySenderidAndTitleContaining(@Param("senderid") String senderid,
                                                   @Param("title") String title, Pageable pageable);

    // 쪽지 삭제
    @Transactional
    @Modifying
    @Query("delete from Message m where m.idx = :idx")
    void delete_message(@Param("idx") Long idx);


    // 메세지 읽음 처리
    @Transactional
    @Modifying
    @Query("update Message set read = '0' where idx =:idx")
    void read(@Param("idx") Long idx);


}
