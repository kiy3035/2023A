package teamgreen.abc.service;

import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import teamgreen.abc.domain.Comment1;
import teamgreen.abc.domain.Commuting1;
import teamgreen.abc.domain.Heart;
import teamgreen.abc.domain.User1;
import teamgreen.abc.repository.CommentRepository;
import teamgreen.abc.repository.CommutingRepository;
import teamgreen.abc.repository.HeartRepository;
import teamgreen.abc.repository.UserRepository;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CommutingService {

    UserRepository userRepository;
    CommutingRepository commutingRepository;
    HeartRepository heartRepository;
    CommentRepository commentRepository;

    // 글 작성 처리
    public void write(Commuting1 commuting1, MultipartFile file) throws Exception {
        commutingRepository.save(commuting1);
    }


    // 등하원 첨부파일 업로드
    public String uploadFile(MultipartFile file) throws IOException {

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_commuting_" + file.getOriginalFilename();

        file.transferTo(new File("C:\\PROJECT\\abc 2023\\src\\main\\resources\\static\\commuting\\" + imageFileName));

        System.out.println("파일은" + imageFileName);
        return imageFileName;
    }


    // 게시글 리스트 처리
    public Page<Commuting1> commuting1List(String commenuidx, Pageable pageable) {

        return commutingRepository.findAllDesc(commenuidx, pageable);
    }


    // 게시글 조회순으로 보기
    public Page<Commuting1> commuting1ListByView(String commenuidx, Pageable pageable) {

        return commutingRepository.findAllByViewDesc(commenuidx, pageable);
    }


    // 게시글 조회순으로 보기
    public Page<Commuting1> commuting1ListByLike(String commenuidx, Pageable pageable) {

        return commutingRepository.findAllByLikeDesc(commenuidx, pageable);
    }


    // 제목으로 게시글 검색
    public Page<Commuting1> commuting1SearchList(String comemenuidx, String comtitle, Pageable pageable) {

        return commutingRepository.findByCommenuidxAndComtitleContaining(comemenuidx, comtitle, pageable);
    }


    // 특정 게시글 불러오기
    public Commuting1 commutingView(Long comidx) {

        return commutingRepository.findById(comidx).get();
    }


    // 조회수
    public void comCount(Long comidx) {

        commutingRepository.comCount(comidx);
    }


    // 파일 다운로드
    public ResponseEntity<InputStreamResource> fileDownload(MultipartFile file, Commuting1 commuting1) throws IOException {


        String filepath = commuting1.getComfilepath();
        System.out.println("파일경로 : " + filepath);

        Path filePath = Paths.get("C:\\PROJECT\\abc 2023\\src\\main\\resources\\static\\commuting\\" + filepath);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(filePath.toString()));

        int index = filepath.lastIndexOf("_");
        String fileName = filepath.substring(index + 1);


        System.out.println("파일 찐네임 : " + fileName);
        System.out.println("다운로드된 파일 : " + filePath);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .cacheControl(CacheControl.noCache())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;" + fileName)
                .body(resource);
    }


    // 좋아요 찾기
    public int findLike(Long comidx, String userid) {
        // 저장된 DTO 가 없다면 0, 있다면 1

        Optional<Heart> findLike = heartRepository.findByCommuting1_ComidxAndUser1_Userid(comidx, userid);

        if (findLike.isEmpty()) {
            return 0;
        } else {
            return 1;
        }
    }


    // 좋아요 저장
    @Transactional
    public int saveLike(Long comidx, String userid) {
        Optional<Heart> findLike = heartRepository.findByCommuting1_ComidxAndUser1_Userid(comidx, userid);

        System.out.println("findLike :" + findLike);
        System.out.println(findLike.isEmpty());

        if (findLike.isEmpty()) {
            User1 user1 = userRepository.findById(userid).get();
            Commuting1 commuting1 = commutingRepository.findById(comidx).get();

            Heart heart = Heart.toHeart(user1, commuting1);
            heartRepository.save(heart);
            commutingRepository.plusLike(comidx);
            return 1;
        } else {
            heartRepository.deleteByCommuting1_ComidxAndUser1_Userid(comidx, userid);
            commutingRepository.minusLike(comidx);
            return 0;
        }
    }


    // 댓글 작성
    public void insertComment(Comment1 comment1) throws Exception{

        commentRepository.save(comment1);
    }


    // 댓글 List
    public List<Comment1> getCommentList(Long commutingidx) throws Exception{
        return commentRepository.getComment(commutingidx);
    }








}

