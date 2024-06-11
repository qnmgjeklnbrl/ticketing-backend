package ticketing.ticket.member.repository;

import ticketing.ticket.member.domain.entity.Member;
import java.util.*;

public interface MemberRepository {
    Member findById(Long memberId);
    void save(Member member);
    Optional<Member> findByEmail(String email);
    
}
