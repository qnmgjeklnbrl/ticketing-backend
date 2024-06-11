package ticketing.ticket.member.domain.dto;

import lombok.Data;
import ticketing.ticket.member.domain.entity.Member;

@Data
public class MemberDto {
    private Long memberId;

    private String name;
    private String email;
    private String password;

    public Member toEntity(){
        Member member = new Member();
        member.setMemberId(this.memberId);
        member.setName(this.name);
        member.setEmail(this.email);
        member.setPassword(this.password);
        return member;

    }
}
