package ticketing.ticket.member.service.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ticketing.ticket.coupon.domain.entity.Coupon;
import ticketing.ticket.coupon.repository.CouponRepository;
import ticketing.ticket.exception.DuplicatedCouponException;
import ticketing.ticket.member.domain.dto.MemberCouponRequestDto;
import ticketing.ticket.member.domain.dto.MemberCouponResponseDto;
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
    public void saveMemberCoupon(MemberCouponRequestDto requestDto) throws DuplicatedCouponException {
       try {
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
       } catch (DataIntegrityViolationException e) {
            Throwable rootCause = e.getRootCause();
            if (rootCause instanceof SQLIntegrityConstraintViolationException) {
                throw new DuplicatedCouponException("이미 발급받은 쿠폰입니다.");
            } else {
                throw e;
            }
       }
    }


    @Override
    public List<MemberCouponResponseDto> findAllByMemberId(Long memberId) {
        return memberCouponRepository.findAllByMemberId(memberId).stream()
                .map(memberCoupon -> MemberCouponResponseDto.builder()
                        .couponId(memberCoupon.getCoupon().getCouponId())
                        .name(memberCoupon.getCoupon().getName())
                        .memberId(memberCoupon.getMember().getMemberId())
                        .endDate(memberCoupon.getCoupon().getEndDate())
                        .percent(memberCoupon.getCoupon().getPercent())
                        .memberCouponId(memberCoupon.getMemberCouponId())
                        .build())
                .collect(Collectors.toList());
    }
}
