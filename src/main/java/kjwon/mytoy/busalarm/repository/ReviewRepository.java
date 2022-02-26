package kjwon.mytoy.busalarm.repository;

import kjwon.mytoy.busalarm.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
