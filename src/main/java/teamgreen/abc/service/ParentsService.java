package teamgreen.abc.service;

import teamgreen.abc.repository.ParentsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ParentsService {

       ParentsRepository parentsRepository;


    // 학부모 프로필 사진 업로드
    public String uploadFile(MultipartFile file) throws IOException {

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid +  "_Parents_profile_" + file.getOriginalFilename() ;

        file.transferTo(new File("C:\\PROJECT\\abc 20230103\\src\\main\\resources\\static\\parents_img\\" +  imageFileName));

    return imageFileName;
    }

}
