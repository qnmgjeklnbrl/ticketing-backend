package ticketing.ticket.member.service;

import org.springframework.http.ResponseEntity;

import ticketing.ticket.member.domain.dto.JwtTokenDto;
import ticketing.ticket.member.domain.dto.MemberDto;
import ticketing.ticket.member.domain.dto.MemberResponse;

public interface MemberService {
    void signUp(MemberDto memberDto);
    void adminSignUp(MemberDto memberDto);
    ResponseEntity<MemberResponse> signIn(String username, String password);
}
