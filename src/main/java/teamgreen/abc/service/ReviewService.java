package teamgreen.abc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import teamgreen.abc.domain.Review1;
import teamgreen.abc.repository.ReviewRepository;

// 전체적인 내용은 BoardService.java와 동일하게 일단은 감
@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // 글 작성 처리, 여기서는 사진이 필요가 없지 않나?
    public void writeReview(Review1 review1) throws Exception {

        reviewRepository.save(review1);
    }

    // 리뷰글 리스트 처리
    public Page<Review1> review1List(Pageable pageable) {

        return reviewRepository.findAll(pageable);
        // findAll 메소드 = review1 클래스가 담긴 List를 반환해줌
    }

    public Page<Review1> review1SearchList(String searchKeyword, Pageable pageable) {

        return reviewRepository.findByReviewtitleContaining(searchKeyword, pageable);
    }

    // 특정 리뷰글 불러오기
    public Review1 reviewView(Long reviewidx) {

        return reviewRepository.findById(reviewidx).get();
    }

    // 특정 리뷰글 삭제
    public void reviewDelete(Long reviewidx) {

        reviewRepository.deleteById(reviewidx);
    }
}
