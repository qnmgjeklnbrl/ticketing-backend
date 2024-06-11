package ticketing.ticket.member.service.impl;



import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;





import java.util.*;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ticketing.ticket.config.JwtTokenProvider;
import ticketing.ticket.member.domain.dto.JwtTokenDto;
import ticketing.ticket.member.domain.dto.LogInMemberDto;
import ticketing.ticket.member.domain.dto.MemberDto;
import ticketing.ticket.member.domain.dto.MemberResponse;
import ticketing.ticket.member.domain.entity.Member;
import ticketing.ticket.member.repository.MemberRepository;
import ticketing.ticket.member.service.MemberService;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;


    @Override
    public void signUp(MemberDto memberDto) {
        Member member = Member.builder()
            .email(memberDto.getEmail())
            .name(memberDto.getName())
            .password(passwordEncoder.encode(memberDto.getPassword()))
            .roles(Collections.singletonList("USER"))
            .build();
       
        memberRepository.save(member);
        
    }
    @Override
    public void adminSignUp(MemberDto memberDto){
        Member member = Member.builder()
            .email(memberDto.getEmail())
            .name(memberDto.getName())
            .password(passwordEncoder.encode(memberDto.getPassword()))
            .roles(Collections.singletonList("ADMIN"))
            .build();

        memberRepository.save(member);
    }

   public ResponseEntity<MemberResponse> signIn(String username, String password) {

        LogInMemberDto userDetails = (LogInMemberDto) customUserDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password);

        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtTokenDto jwtToken = jwtTokenProvider.generateToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtToken.getAccessToken());

        Member member = ((LogInMemberDto) authentication.getPrincipal()).getMember();

        String authority = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .findFirst()
            .orElse(null);
        System.out.println("authority: " + authority);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(MemberResponse.builder()
                        .memberId(member.getMemberId())
                        .name(member.getName())
                        .email(member.getEmail())
                        .authority(member.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.joining()))
                        .build());
    }

    
}
