package kjwon.mytoy.busalarm.service;

import kjwon.mytoy.busalarm.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    boolean registerReview(ReviewDto reviewDto, String userName);
    List<ReviewDto> getReview();
}
