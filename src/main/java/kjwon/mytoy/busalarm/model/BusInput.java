package kjwon.mytoy.busalarm.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class BusInput {
    String busName;
    String busStation;
    LocalDateTime destinationTime;
}
