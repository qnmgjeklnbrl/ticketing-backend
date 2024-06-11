package ticketing.ticket.member.repository.impl;


import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;

import jakarta.persistence.PersistenceContext;
import ticketing.ticket.member.domain.entity.MemberCoupon;
import ticketing.ticket.member.repository.MemberCouponRepository;

@Repository

public class MemberCouponRepositoryImpl implements MemberCouponRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(MemberCoupon memberCoupon) {
        em.persist(memberCoupon);
    }
}
