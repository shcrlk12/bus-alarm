package kjwon.mytoy.busalarm.service;

//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kjwon.mytoy.busalarm.model.MemberInput;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface MemberService extends UserDetailsService {
    boolean register(MemberInput parameter);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
