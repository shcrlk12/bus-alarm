package kjwon.mytoy.busalarm.configuration;

import kjwon.mytoy.busalarm.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    private final MemberService memberService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginIntercepter());

        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Bean
    public LoginIntercepter loginIntercepter() {
        return new LoginIntercepter(memberService);
    }
}
