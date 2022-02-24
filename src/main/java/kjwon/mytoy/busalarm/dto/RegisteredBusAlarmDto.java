package kjwon.mytoy.busalarm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisteredBusAlarmDto {
    String time;
    String busStationName;
    boolean isActive;
    Long id;
}
