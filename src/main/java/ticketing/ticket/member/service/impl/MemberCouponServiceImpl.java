package ticketing.ticket.member.service.impl;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ticketing.ticket.coupon.domain.entity.Coupon;
import ticketing.ticket.coupon.repository.CouponRepository;
import ticketing.ticket.member.domain.dto.MemberCouponRequestDto;
import ticketing.ticket.member.domain.entity.MemberCoupon;
import ticketing.ticket.member.repository.MemberCouponRepository;
import ticketing.ticket.member.repository.MemberRepository;
import ticketing.ticket.member.service.MemberCouponService;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCouponServiceImpl implements MemberCouponService{
    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    @Override
    public void saveMemberCoupon(MemberCouponRequestDto requestDto) {
       Coupon coupon = couponRepository.findById(requestDto.getCouponId());
       //쿠폰 수량 확인
       if (coupon.getQuantity() <= 0) {
            System.out.println("남은 쿠폰 수량 없음");
            return;
       }
       MemberCoupon memberCoupon = new MemberCoupon();
       memberCoupon.setMember(memberRepository.findById(requestDto.getMemberId()));
       memberCoupon.setCoupon(coupon);
       

       coupon.setQuantity(coupon.getQuantity() - 1);
       couponRepository.save(coupon);
       memberCouponRepository.save(memberCoupon);
    }
}
