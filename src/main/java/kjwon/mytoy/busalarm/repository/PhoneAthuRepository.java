package kjwon.mytoy.busalarm.repository;

import kjwon.mytoy.busalarm.entity.PhoneAthu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneAthuRepository extends JpaRepository<PhoneAthu, Long> {
    Optional<PhoneAthu> findByPhone(String phone);
}
