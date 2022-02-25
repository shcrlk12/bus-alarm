package kjwon.mytoy.busalarm.configuration;

import kjwon.mytoy.busalarm.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RequiredArgsConstructor
public class LoginIntercepter implements HandlerInterceptor {

    private final MemberService memberService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean isLogin = !principal.equals("anonymousUser");
        UserDetails userDetails;
        String userName = null;
        Long myPoint = Long.parseLong("0");
        if(isLogin) {
            userDetails = (UserDetails) principal;
            userName = userDetails.getUsername().split("@")[0];
            myPoint = memberService.inquiryPoint(userDetails.getUsername());
        }
        request.setAttribute("isLogin",isLogin);
        request.setAttribute("userName",userName);
        request.setAttribute("myPoint",myPoint);


        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
