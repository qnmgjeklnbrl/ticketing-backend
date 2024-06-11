package ticketing.ticket.member.repository.impl;


import org.springframework.stereotype.Repository;
import java.util.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ticketing.ticket.member.domain.entity.Member;
import ticketing.ticket.member.repository.MemberRepository;
@Repository
public class MemberRepositoryImpl  implements MemberRepository{
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public Member findById(Long memberId) {
      return em.find(Member.class, memberId);
    }
    @Override
    public void save(Member member) {
      if (member.getMemberId() == null) {
        em.persist(member);
      } else {
        em.merge(member);
      }
    }
    @Override
    public Optional<Member> findByEmail(String email) {
      Member member = em.createQuery("select m from Member m where m.email = :email",Member.class)
                          .setParameter("email", email).getSingleResult();
      return Optional.ofNullable(member);
    }
    
}
