package teamgreen.abc.service;

import teamgreen.abc.domain.Helper1;
import teamgreen.abc.repository.HelperRepository;
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
public class HelperService {

       HelperRepository helperRepository;

    // 헬퍼 프로필 사진 업로드
    public String uploadFile(MultipartFile file) throws IOException {

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid +  "_Helper_profile_" + file.getOriginalFilename() ;

        file.transferTo(new File("C:\\PROJECT\\abc 2023\\src\\main\\resources\\static\\helper_img\\" +  imageFileName));

    return imageFileName;
    }


    @Transactional
    public Helper1 getHelper(String helperid) {
        Optional<Helper1> helperWrapper = Optional.ofNullable(helperRepository.findByHelperid(helperid));
        if (helperWrapper.isPresent())
        {
            Helper1 helper1 = helperWrapper.get();

            Helper1 helperDetail = Helper1.builder()
                    .helperid(helper1.getHelperid())
                    .helper_addr(helper1.getHelper_addr())
                    .helper_comment(helper1.getHelper_comment())
                    .helper_reccount(helper1.getHelper_reccount())
                    .helper_go(helper1.getHelper_go())
                    .helper_come(helper1.getHelper_come())
                    .helper_filepath(helper1.getHelper_filepath())
                    .build();

            return helperDetail;
        }

        return null;
    }
}
