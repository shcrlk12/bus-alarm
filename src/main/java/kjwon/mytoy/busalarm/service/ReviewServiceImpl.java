package kjwon.mytoy.busalarm.service;

import kjwon.mytoy.busalarm.dto.ReviewDto;
import kjwon.mytoy.busalarm.entity.Review;
import kjwon.mytoy.busalarm.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public boolean registerReview(ReviewDto reviewDto, String userName) {
        Review review = Review.builder()
                .content(reviewDto.getReviewContent())
                .regDt(LocalDateTime.now())
                .station(reviewDto.getStation())
                .isDelete(false)
                .accuracy(reviewDto.getAccuracy())
                .userId(userName)
                .build();

        reviewRepository.save(review);
        return false;
    }

    @Override
    public List<ReviewDto> getReview() {
        List<Review> reviewsList = reviewRepository.findAll();

        if(reviewsList.isEmpty())
            return Collections.emptyList();

        return ReviewDto.of(reviewsList);
    }
}
