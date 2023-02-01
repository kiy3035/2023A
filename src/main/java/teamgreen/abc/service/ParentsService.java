package teamgreen.abc.service;

import teamgreen.abc.domain.Parents1;
import teamgreen.abc.repository.ParentsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ParentsService {

       ParentsRepository parentsRepository;

    // 학부모 프로필 사진 업로드
    public String uploadFile(MultipartFile file) throws IOException {

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid +  "_Parents_profile_" + file.getOriginalFilename() ;

        file.transferTo(new File("C:\\PROJECT\\abc 2023\\src\\main\\resources\\static\\parents_img\\" +  imageFileName));

    return imageFileName;
    }


    @Transactional
    public Parents1 getParents(String parentsid){
        Optional<Parents1> parentsWrapper = Optional.ofNullable(parentsRepository.findByParentsid(parentsid));
        if(parentsWrapper.isPresent())
        {
            Parents1 parents1 = parentsWrapper.get();

            Parents1 parentsDetail = Parents1.builder()
                    .parentsid(parents1.getParentsid())
                    .parents_addr(parents1.getParents_addr())
                    .parents_comment(parents1.getParents_comment())
                    .parents_kakao(parents1.getParents_kakao())
                    .parents_come(parents1.getParents_come())
                    .parents_go(parents1.getParents_go())
                    .parents_filepath(parents1.getParents_filepath())
                    .build();

            return parentsDetail;
        }

        return null;
    }
}
