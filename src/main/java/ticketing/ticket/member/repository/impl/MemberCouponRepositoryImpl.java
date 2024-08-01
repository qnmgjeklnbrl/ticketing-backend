package ticketing.ticket.member.repository.impl;


import java.util.List;

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
        if(memberCoupon.getMemberCouponId() == null){
            em.persist(memberCoupon);
        }else{
            em.merge(memberCoupon);
        }
    }

    @Override
    public List<MemberCoupon> findAllByMemberId(Long memberId) {
        return em.createQuery("select mc from MemberCoupon mc join fetch mc.coupon c where mc.member.id = :memberId and mc.isUsed = false", MemberCoupon.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public MemberCoupon findById(Long memberCouponId) {
        return em.find(MemberCoupon.class, memberCouponId);
    }

    @Override
    public void delete(MemberCoupon memberCoupon) {
        em.remove(memberCoupon);
    }
}
