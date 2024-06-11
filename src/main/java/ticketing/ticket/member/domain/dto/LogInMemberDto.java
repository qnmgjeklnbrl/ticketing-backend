package ticketing.ticket.member.domain.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;

import lombok.Getter;
import ticketing.ticket.member.domain.entity.Member;


@Getter
public class LogInMemberDto extends User {
    private final Member member;

    public LogInMemberDto(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getUsername(), member.getPassword(), authorities);
        this.member = member;
    }
}