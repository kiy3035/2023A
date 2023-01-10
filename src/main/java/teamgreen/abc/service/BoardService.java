package teamgreen.abc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import teamgreen.abc.domain.Board1;
import teamgreen.abc.repository.BoardRepository;

import java.io.File;
import java.util.UUID;

@Service
public class BoardService {

    private BoardRepository boardRepository;

    // 이거 아니면 @Autowired로 하는 방법도 있음
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 글 작성 처리
    public void write(Board1 board1, MultipartFile file) throws Exception {

        // 저장할 파일 경로 지정
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        // 식별자 - 파일의 랜덤 이름 생성
        UUID uuid = UUID.randomUUID();

        // 실제 저장될 파일 이름 생성
        // if () {}
        String fileName = uuid + "_" + file.getOriginalFilename();

        // 새로만든 클래스가 윗 경로 + 해당하는 파일 이름으로 저장될 것임
        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        board1.setPostfilename(fileName);
        board1.setPostfilepath("/files/" + fileName);

        boardRepository.save(board1);
    }

    // 게시글 리스트 처리
    public Page<Board1> board1List(Pageable pageable) {

        return boardRepository.findAll(pageable);
        // findAll 메소드 = board1 클래스가 담긴 List를 반환해줌
    }

    public Page<Board1> board1SearchList(String searchKeyword, Pageable pageable) {

        return boardRepository.findByPosttitleContaining(searchKeyword, pageable);
    }

    // 특정 게시글 불러오기
    public Board1 boardView(Long postidx) {

        return boardRepository.findById(postidx).get();
    }

    // 특정 게시글 삭제
    public void boardDelete(Long postidx) {

        boardRepository.deleteById(postidx);
    }
}
