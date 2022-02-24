package kjwon.mytoy.busalarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BusAlarmApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusAlarmApplication.class, args);
    }

}
