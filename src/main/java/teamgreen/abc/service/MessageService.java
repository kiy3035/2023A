package teamgreen.abc.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import teamgreen.abc.domain.Commuting1;
import teamgreen.abc.domain.Message;
import teamgreen.abc.repository.MessageRepository;

import javax.transaction.Transactional;


@Service
@AllArgsConstructor
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;


    // 받은 쪽지함
    @Transactional
    public Page<Message> receiveMessageList(String receiverid, Pageable pageable) {
        return messageRepository.receiveMessageList(receiverid, pageable);
    }


    // 쪽지 검색 [받은 쪽지함]
    @Transactional
    public Page<Message> search(String receiverid, String title, Pageable pageable) {

        return messageRepository.findByReceiveridAndTitleContaining(receiverid, title, pageable);
    }


    // 보낸 쪽지함
    @Transactional
    public Page<Message> sendMessageList(String senderid, Pageable pageable) {

        return messageRepository.sendMessageList(senderid, pageable);
    }


    // 쪽지 검색 [보낸 쪽지함]
    @Transactional
    public Page<Message> search2(String senderid, String title, Pageable pageable) {

        return messageRepository.findBySenderidAndTitleContaining(senderid, title, pageable);
    }


}
