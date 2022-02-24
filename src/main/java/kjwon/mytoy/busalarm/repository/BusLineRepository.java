package kjwon.mytoy.busalarm.repository;

import kjwon.mytoy.busalarm.entity.BusLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusLineRepository extends JpaRepository<BusLine, Long> {
    Optional<BusLine> findByBusName(String busName);
}
