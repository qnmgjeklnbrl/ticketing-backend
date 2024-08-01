package ticketing.ticket.member.repository;

import java.util.List;

import ticketing.ticket.member.domain.entity.MemberCoupon;

public interface MemberCouponRepository {
    void save(MemberCoupon memberCoupon);
    List<MemberCoupon> findAllByMemberId(Long memberId);
    MemberCoupon findById(Long memberCouponId);
    void delete(MemberCoupon memberCoupon);
    
}
