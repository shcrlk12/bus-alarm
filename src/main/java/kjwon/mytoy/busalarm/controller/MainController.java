package kjwon.mytoy.busalarm.controller;

import kjwon.mytoy.busalarm.dto.BusInfoDto;
import kjwon.mytoy.busalarm.dto.RegisteredBusAlarmDto;
import kjwon.mytoy.busalarm.model.BusLineOutput;
import kjwon.mytoy.busalarm.model.MemberInput;
import kjwon.mytoy.busalarm.service.BusService;
import kjwon.mytoy.busalarm.service.BusServiceImpl;
import kjwon.mytoy.busalarm.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final MemberService memberService;
    private final BusService busService;

    private  static final Logger log = Logger.getLogger(MainController.class);

    @GetMapping("/")
    public String index(Principal principal){

        if(principal != null)
        log.debug(principal.getName());
        return "index";
    }

    /**
     * member controller
     * */
    @GetMapping("/member/login")
    public String login(){

        return "member/login";
    }

    @GetMapping("/member/register")
    public String register(){

        return "member/register";
    }

    @PostMapping("/member/register")
    public String registerSubmit(MemberInput parameter){
        System.out.println("test");

        System.out.println(parameter.getUserName());

        boolean result = memberService.register(parameter);

        return "index";
    }

    @GetMapping("/member/userInfo")
    public String userInfo(Model model, Principal principal){

        Long myPoint;

        myPoint = memberService.inquiryPoint(principal.getName());
        model.addAttribute("myPoint", myPoint);

        return "member/userInfo";
    }

    @GetMapping("/member/point-charge")
    public String pointCharge(Model model, Principal principal){

        Long myPoint;

        myPoint = memberService.inquiryPoint(principal.getName());
        model.addAttribute("myPoint", myPoint);

        return "member/point-charge";
    }

    /**
     * bus controller
     * */

    @GetMapping("/bus/subscribe")
    public String subscribeBusPage(String busName, Model model, Principal principal) throws IOException {
        model.addAttribute("busName", busName);

        if(busName != null) {
            BusLineOutput busLineOutput = busService.searchBusRoute(busName);

            model.addAttribute("busStopList", busLineOutput.getBusLine());
            model.addAttribute("arsIdList", busLineOutput.getBusStationArsId());
            model.addAttribute("Lng", busLineOutput.getGpsX());
            model.addAttribute("Lat", busLineOutput.getGpsY());
            System.out.println(busLineOutput.getGpsX() +" Tmx");

        }
        List<RegisteredBusAlarmDto> registeredBusAlarmDtoList = busService.getRegisterAlarm(principal);

        model.addAttribute("regList",registeredBusAlarmDtoList);

        Long myPoint = memberService.inquiryPoint(principal.getName());

        model.addAttribute("myPoint",myPoint);

        return "/bus/subscribe";
    }

    @PostMapping("/bus/subscribe")
    public String subscribeBus(BusInfoDto busInfoDto, Principal principal) throws IOException {

        log.debug(principal.getName());
        log.debug(busInfoDto.getBusStop());
        busService.registerBusAlarm(busInfoDto, principal);

        return "redirect:/";
    }

    @GetMapping("/bus/deleteSubscribe")
    public String deleteSubscribe(Long id) throws IOException {

        log.debug(id);
//        busService.registerBusAlarm(busInfoDto, principal);
        busService.deleteSubscribe(id);
        return "redirect:/";
    }


}
