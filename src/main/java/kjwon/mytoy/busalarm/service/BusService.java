package kjwon.mytoy.busalarm.service;

import kjwon.mytoy.busalarm.dto.BusInfoDto;
import kjwon.mytoy.busalarm.dto.RegisteredBusAlarmDto;
import kjwon.mytoy.busalarm.model.BusLineOutput;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface BusService {
    BusLineOutput searchBusRoute(String busName) throws IOException;

    void registerBusAlarm(BusInfoDto busInfoDto, Principal principal) throws IOException;

    List<RegisteredBusAlarmDto> getRegisterAlarm(Principal principal);

    boolean deleteSubscribe(Long id);

}
