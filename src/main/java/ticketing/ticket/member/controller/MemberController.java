package ticketing.ticket.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ticketing.ticket.member.domain.dto.JwtTokenDto;
import ticketing.ticket.member.domain.dto.LoginDto;
import ticketing.ticket.member.domain.dto.MemberDto;
import ticketing.ticket.member.domain.dto.MemberResponse;
import ticketing.ticket.member.service.MemberService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> setMember (@RequestBody MemberDto memberDto) {
        memberService.signUp(memberDto);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/signin")
    public ResponseEntity<MemberResponse> signIn(@RequestBody LoginDto loginDto){

      return memberService.signIn(loginDto.getEmail(), loginDto.getPassword());
     
    }

    //////////관리자 회원가입/////////////////////

    @PostMapping("/admin-signup")
    public ResponseEntity<Void> adminSignUp(@RequestBody MemberDto memberDto){
        memberService.adminSignUp(memberDto);
        return ResponseEntity.ok().build();
    }

    

}
