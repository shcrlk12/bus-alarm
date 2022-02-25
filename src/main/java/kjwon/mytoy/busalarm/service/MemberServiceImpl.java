package kjwon.mytoy.busalarm.service;

//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kjwon.mytoy.busalarm.entity.Member;
import kjwon.mytoy.busalarm.exception.RepositoryException;
import kjwon.mytoy.busalarm.model.MemberInput;
import kjwon.mytoy.busalarm.repository.MemberRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    MemberRepository memberRepository;
    private  static final Logger log = Logger.getLogger(MemberServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findById(username);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();
/*
        if (Member.MEMBER_STATUS_REQ.equals(member.getUserStatus())) {
            throw new MemberNotEmailAuthException("이메일 활성화 이후에 로그인을 해주세요.");
        }

        if (Member.MEMBER_STATUS_STOP.equals(member.getUserStatus())) {
            throw new MemberStopUserException("정지된 회원 입니다.");
        }

        if (Member.MEMBER_STATUS_WITHDRAW.equals(member.getUserStatus())) {
            throw new MemberStopUserException("탈퇴된 회원 입니다.");
        }
        */
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
/*
        if (member.isAdminYn()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }*/

        return new User(member.getUserId(), member.getPassword(), grantedAuthorities);
//        return null;
    }

    public boolean register(MemberInput parameter) {

        Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
        if (optionalMember.isPresent()) {
            //현재 userId에 해당하는 데이터 존재
            return false;
        }

        String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());
        String uuid = UUID.randomUUID().toString();

        Member member = Member.builder()
                .userId(parameter.getUserId())
                .userName(parameter.getUserName())
                .phone(parameter.getPhone())
                .password(encPassword)
                .regDt(LocalDateTime.now())
                .build();
        memberRepository.save(member);

        return true;
    }

    @Override
    public Long inquiryPoint(String userName) {
        Optional<Member> optionalMember = memberRepository.findById(userName);

        Member member = optionalMember.orElseThrow(() ->
                new RepositoryException(1, "아이디가 없습니다.")
        );

        log.debug("my Point : " + member.getMyPoint());

        return member.getMyPoint();
    }

    @Override
    public boolean chargePoint(Long chargePoint, String userName) {

        Optional<Member> optionalMember = memberRepository.findById(userName);

        Member member = optionalMember.orElseThrow(()-> new RepositoryException(1, "아이디가 없습니다."));

        if(member.getMyPoint() == null)
            member.setMyPoint(Long.parseLong("0"));

        member.setMyPoint(member.getMyPoint() + chargePoint);

        memberRepository.save(member);
        return true;
    }
}
