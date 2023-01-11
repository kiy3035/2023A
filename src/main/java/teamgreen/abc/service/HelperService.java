package teamgreen.abc.service;


import teamgreen.abc.repository.HelperRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class HelperService {

       HelperRepository helperRepository;

    // 헬퍼 프로필 사진 업로드
    public String uploadFile(MultipartFile file) throws IOException {

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid +  "_Helper_profile_" + file.getOriginalFilename() ;

        file.transferTo(new File("C:\\PROJECT\\abc 2023\\src\\main\\resources\\static\\helper_img\\" +  imageFileName));

    return imageFileName;
    }


}
