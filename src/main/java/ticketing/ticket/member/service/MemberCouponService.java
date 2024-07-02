package ticketing.ticket.member.service;

import java.util.List;

import ticketing.ticket.member.domain.dto.MemberCouponRequestDto;
import ticketing.ticket.member.domain.dto.MemberCouponResponseDto;

public interface MemberCouponService {
    void saveMemberCoupon(MemberCouponRequestDto requestDto);
    List<MemberCouponResponseDto> findAllByMemberId(Long memberId);
}
