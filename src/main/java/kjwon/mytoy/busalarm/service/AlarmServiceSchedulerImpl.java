package kjwon.mytoy.busalarm.service;

import kjwon.mytoy.busalarm.entity.SubscribeBusAlarm;
import kjwon.mytoy.busalarm.exception.RepositoryException;
import kjwon.mytoy.busalarm.repository.SubscribeBusAlarmRepository;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AlarmServiceSchedulerImpl implements AlarmServiceScheduler{

    private  static final Logger log = Logger.getLogger(AlarmServiceSchedulerImpl.class);
    private final SubscribeBusAlarmRepository subscribeBusAlarmRepository;

    @Scheduled(initialDelay = 10000, fixedRate = 30000)
    public void runAlarmService(){
        List<SubscribeBusAlarm> subscribeBusAlarmList = subscribeBusAlarmRepository.findByIsActive(true);

        for(SubscribeBusAlarm subscribeBusAlarm : subscribeBusAlarmList) {
            String[] str = subscribeBusAlarm.getAlarmTime().split(":");

            int alarmTime = (Integer.parseInt(str[0]) * 60) + Integer.parseInt(str[1]);
            int currentTime = (LocalDateTime.now().getHour() * 60) + LocalDateTime.now().getMinute();

            boolean isSmsMsg;

            log.debug(subscribeBusAlarm.getUserId() + "alarm Time : " + subscribeBusAlarm.getAlarmTime()
                    + ", current Time" + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());

            if (Math.abs(alarmTime - currentTime) <= 5) {
                log.info("send sms message to " + subscribeBusAlarm.getUserId());
                isSmsMsg = true;
            }
            else
                isSmsMsg = false;

        }
    }
}
