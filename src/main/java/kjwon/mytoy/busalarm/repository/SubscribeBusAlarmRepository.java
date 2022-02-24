package kjwon.mytoy.busalarm.repository;

import kjwon.mytoy.busalarm.entity.SubscribeBusAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscribeBusAlarmRepository extends JpaRepository<SubscribeBusAlarm, Long> {
    List<SubscribeBusAlarm> findByIsActive(boolean isActive);
    List<SubscribeBusAlarm> findByIsDelete(boolean isDelete);

}
