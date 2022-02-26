package kjwon.mytoy.busalarm.controller;

import kjwon.mytoy.busalarm.dto.ReviewDto;
import kjwon.mytoy.busalarm.exception.RepositoryException;
import kjwon.mytoy.busalarm.entity.BusLine;
import kjwon.mytoy.busalarm.model.BusInput;
import kjwon.mytoy.busalarm.model.BusLineOutput;
import kjwon.mytoy.busalarm.repository.BusLineRepository;
import kjwon.mytoy.busalarm.service.BusService;
import kjwon.mytoy.busalarm.service.MemberService;
import kjwon.mytoy.busalarm.service.ReviewService;
import kjwon.mytoy.busalarm.service.SmsService;
import kjwon.mytoy.busalarm.util.SeoulBusAPICall;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@RestController
public class BusRestController {

    private  static final org.apache.log4j.Logger log = Logger.getLogger(BusRestController.class);

    private final BusService busService;
    private final MemberService memberService;
    private final ReviewService reviewService;
    private final SmsService smsService;

    @ExceptionHandler(Exception.class)
    public Object nullex(Exception e) {
        e.printStackTrace();
        return "Error";
    }

    @PostMapping("/api/v1/searchPath")
    public String v1_searchPath(String busPath, String dest) {
        //1. buspath
        return "test";
    }

    @GetMapping("/api/v1/subscribeBus")
    public String v1_subscribeBus(BusInput parameters) throws IOException{
        //1. station 이름으로 station 정보 검색
        SeoulBusAPICall seoulBusAPICall = new SeoulBusAPICall();

        Document doc = seoulBusAPICall.getStationByNameList("미아사거리전철역");

        Elements elements = SeoulBusAPICall.getItemList(doc);

        for(Element e : elements)
        {
            System.out.println(SeoulBusAPICall.getArsId(e));
        }
        //2. 잘못된 station이면 Throw
        //3. scheulder 등록
        //4. 등록된 번호로 문자 보냄.


        return "test";
    }

    @PostMapping("/api/v1/detachBus")
    public String v1_detachBus() {

        return "test";
    }

    /**
     * bus이름을 입력받으면, 그 버스의 경로를 리턴
     * @param busName
     * */
    @GetMapping("/api/v1/busLine")
    public BusLineOutput v1_busLine(String busName) throws IOException {
        return busService.searchBusRoute(busName);
    }

    @PostMapping("/api/v1/chargePoint")
    public void v1_chargePoint(Long chargePoint, Principal principal) throws IOException {
        log.info("userName : " + principal.getName() + "charge Point : " + chargePoint);

        memberService.chargePoint(chargePoint, principal.getName());
    }

    @PostMapping("/api/v1/phoneAthu")
    public void v1_phoneAhtu(String phone) {

        smsService.getPhoneAthu(phone);
    }

    @PostMapping("/api/v1/checkPhoneAhtu")
    public boolean v1_checkPhoneAhtu(String phone, String ahtuNumber) {

        return smsService.checkPhoneAhtu(phone, ahtuNumber);
    }


    @PostMapping("/api/v1/registReview")
    public void v1_RegistReview(ReviewDto reviewDto, Principal principal) throws IOException {

        reviewService.registerReview(reviewDto, principal.getName());
//        memberService.chargePoint(chargePoint, principal.getName());
    }
}
