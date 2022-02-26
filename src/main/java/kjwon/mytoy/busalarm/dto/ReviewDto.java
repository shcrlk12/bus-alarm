package kjwon.mytoy.busalarm.dto;

import kjwon.mytoy.busalarm.entity.Review;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    String station;
    String accuracy;
    String reviewContent;
    String userId;
    LocalDateTime regDt;

    public static List<ReviewDto> of (List<Review> reviews) {
        if (reviews != null) {
            List<ReviewDto> reviewList = new ArrayList<>();
            for(Review x : reviews) {
                reviewList.add(of(x));
            }
            return reviewList;
        }

        return null;
    }

    public static ReviewDto of(Review review) {
        return ReviewDto.builder()
                .station(review.getStation())
                .accuracy(review.getAccuracy())
                .reviewContent(review.getContent())
                .userId(review.getUserId())
                .regDt(review.getRegDt())
                .build();
    }
}
