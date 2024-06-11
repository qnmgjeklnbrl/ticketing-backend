package ticketing.ticket.member.service;

import ticketing.ticket.member.domain.dto.MemberCouponRequestDto;

public interface MemberCouponService {
    void saveMemberCoupon(MemberCouponRequestDto requestDto);
}
