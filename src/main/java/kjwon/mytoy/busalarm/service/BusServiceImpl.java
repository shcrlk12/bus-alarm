package kjwon.mytoy.busalarm.service;

import kjwon.mytoy.busalarm.dto.RegisteredBusAlarmDto;
import kjwon.mytoy.busalarm.exception.RepositoryException;
import kjwon.mytoy.busalarm.controller.BusRestController;
import kjwon.mytoy.busalarm.dto.BusInfoDto;
import kjwon.mytoy.busalarm.entity.BusLine;
import kjwon.mytoy.busalarm.entity.SubscribeBusAlarm;
import kjwon.mytoy.busalarm.model.BusLineOutput;
import kjwon.mytoy.busalarm.repository.BusLineRepository;
import kjwon.mytoy.busalarm.repository.SubscribeBusAlarmRepository;
import kjwon.mytoy.busalarm.util.SeoulBusAPICall;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BusServiceImpl implements BusService{

    private final SubscribeBusAlarmRepository subscribeBusAlarmRepository;
    private final BusLineRepository busLineRepository;
    private  static final Logger log = Logger.getLogger(BusServiceImpl.class);

    @Override
    public BusLineOutput searchBusRoute(String busName) throws IOException {
        if(busName == null)
            throw new RepositoryException(2, "버스이름이 비어있습니다.");

        //1. Bus 노선명 DB에서 검색
        Optional<BusLine> optionalBusLine = busLineRepository.findByBusName(busName);
        BusLine busLine = optionalBusLine.orElseThrow(() -> new RepositoryException(1, "버스 이름이 정확하지 않습니다."));

        log.debug("Bus Route Id : " + busLine.getBusRouteId().toString());

        //2. BusRouteId로 경유 정류장 탐색
        SeoulBusAPICall seoulBusAPICall = new SeoulBusAPICall();
        Document doc = seoulBusAPICall.getStaionsByRouteList(busLine.getBusRouteId().toString());

        Elements elements = SeoulBusAPICall.getItemList(doc);

        List<String> arrList = new ArrayList<>();
        List<String> arrList2 = new ArrayList<>();
        List<String> arrList3 = new ArrayList<>();
        List<String> arrList4 = new ArrayList<>();

        BusLineOutput busLineOutput = new BusLineOutput();

        for(Element e : elements){
            arrList.add(SeoulBusAPICall.getSationName(e));
            arrList2.add(SeoulBusAPICall.getArsId(e));
            arrList3.add(SeoulBusAPICall.getGpsX(e));
            arrList4.add(SeoulBusAPICall.getGpsY(e));
        }

        //3. 경유정류장 return
        busLineOutput.setBusLine(arrList);
        busLineOutput.setBusStationArsId(arrList2);
        busLineOutput.setGpsX(arrList3);
        busLineOutput.setGpsY(arrList4);

        return busLineOutput;
    }

    @Override
    public void registerBusAlarm(BusInfoDto busInfoDto, Principal principal) throws IOException {
        log.info("busService registerBusAlarm excute.");
        /*
        SeoulBusAPICall seoulBusAPICall = new SeoulBusAPICall();

        Document doc = seoulBusAPICall.getStationByUidItem(busInfoDto.getBusStop());

        String arriveMsg1 = null;
        String arriveMsg2 = null;

        Elements elements = SeoulBusAPICall.getItemList(doc);
        for(Element e : elements){
            if(SeoulBusAPICall.getRtName(e).equals(busInfoDto.getBusName()))
            {
                arriveMsg1 = SeoulBusAPICall.getArriveMsg1(e);
                arriveMsg2 = SeoulBusAPICall.getArriveMsg2(e);
            }
        }
        log.debug(arriveMsg1);
        log.debug(arriveMsg2);
        */
        String[] str = busInfoDto.getBusStop().split(",");
        SubscribeBusAlarm subscribeBusAlarm = SubscribeBusAlarm.builder()
                .userId(principal.getName())
                .busStationName(str[0])
                .busRouteId(str[1])
                .isActive(true)
                .alarmTime(busInfoDto.getTime())
                .regDt(LocalDateTime.now())
                .build();

        subscribeBusAlarmRepository.save(subscribeBusAlarm);
    }

    @Override
    public List<RegisteredBusAlarmDto> getRegisterAlarm(Principal principal) {
        List<SubscribeBusAlarm> subscribeBusAlarmList = subscribeBusAlarmRepository.findByIsDelete(false);
        List<RegisteredBusAlarmDto> registeredBusAlarmDtoList = new ArrayList<>();

        for(SubscribeBusAlarm s : subscribeBusAlarmList){
            RegisteredBusAlarmDto registeredBusAlarmDto = new RegisteredBusAlarmDto();

            registeredBusAlarmDto.setBusStationName(s.getBusStationName());
            registeredBusAlarmDto.setActive(s.isActive());
            registeredBusAlarmDto.setTime(s.getAlarmTime());
            registeredBusAlarmDto.setId(s.getId());
            registeredBusAlarmDtoList.add(registeredBusAlarmDto);
        }
        return registeredBusAlarmDtoList;
    }

    @Override
    public boolean deleteSubscribe(Long id) {

        Optional<SubscribeBusAlarm> optionalSubscribeBusAlarm = subscribeBusAlarmRepository.findById(id);

        SubscribeBusAlarm subscribeBusAlarm = optionalSubscribeBusAlarm.orElseThrow(() ->
                new RepositoryException(1, "없습니다.")
        );

        subscribeBusAlarm.setDelete(true);
        subscribeBusAlarmRepository.save(subscribeBusAlarm);
        return true;
    }

    @Override
    public boolean isStation(String StationName) throws IOException {

        SeoulBusAPICall seoulBusAPICall = new SeoulBusAPICall();

        Document doc = seoulBusAPICall.getStationByNameList(StationName);

        String headerMsg = SeoulBusAPICall.getHeaderMsg(doc);

        return !headerMsg.equals("결과가 없습니다.");
    }
}
