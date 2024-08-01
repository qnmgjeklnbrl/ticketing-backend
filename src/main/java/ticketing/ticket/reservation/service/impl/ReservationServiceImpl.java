package ticketing.ticket.reservation.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ticketing.ticket.coupon.domain.entity.Coupon;
import ticketing.ticket.coupon.repository.CouponRepository;
import ticketing.ticket.exception.DuplicationReservationException;
import ticketing.ticket.member.domain.entity.MemberCoupon;
import ticketing.ticket.member.repository.MemberCouponRepository;
import ticketing.ticket.member.repository.MemberRepository;
import ticketing.ticket.reservation.domain.dto.CalPriceRequsetDto;
import ticketing.ticket.reservation.domain.dto.MemberSeatReservationResponseDto;
import ticketing.ticket.reservation.domain.dto.ReservationRequestDto;
import ticketing.ticket.reservation.domain.dto.SeatReservationResponseDto;
import ticketing.ticket.reservation.domain.entity.MemberSeatReservation;
import ticketing.ticket.reservation.domain.entity.SeatReservation;
import ticketing.ticket.reservation.repository.MemberSeatReservationRepository;
import ticketing.ticket.reservation.repository.SeatReservationRepository;
import ticketing.ticket.reservation.service.ReservationService;
@Service
@Transactional
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final SeatReservationRepository seatReservationRepository;
    private final MemberSeatReservationRepository memberSeatReservationRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
   

    @Override
    public void setReservation(ReservationRequestDto reservationRequestDto) throws DuplicationReservationException{
       
       try {
        SeatReservation seatReservation = seatReservationRepository.findById(reservationRequestDto.getSeatReservationId());
        Long memberCouponId = reservationRequestDto.getMemberCouponId();
        Optional<MemberCoupon> memberCoupon = Optional.ofNullable(memberCouponId != null ? memberCouponRepository.findById(memberCouponId) : null);
       

       if (seatReservation.isAvailable()) {
           seatReservation.setAvailable(false);
           MemberSeatReservation memberSeatReservation = new MemberSeatReservation();
           memberSeatReservation.setMember( memberRepository.findById( reservationRequestDto.getMemberId() ) );
           memberSeatReservation.setSeatReservation(seatReservation);
           memberSeatReservation.setTotalPrice(reservationRequestDto.getTotalPrice());
           seatReservationRepository.save(seatReservation);
           memberSeatReservationRepository.save(memberSeatReservation);
           memberCoupon.ifPresent(mc -> mc.setUsed(true));
           memberCouponRepository.save(memberCoupon.get());
        } else {
            throw new DuplicationReservationException("이미 예약된 좌석입니다.");
        }
       } catch (ObjectOptimisticLockingFailureException e) {
            throw new DuplicationReservationException("이미 예약된 좌석입니다.");
       }
    }

    @Override
    public List<SeatReservationResponseDto> getSeatReservationListByPerformanceDetail(Long performDetailId) {
        List<SeatReservation> seatReservationList =  seatReservationRepository.findByperformDetailId(performDetailId);
        List<SeatReservationResponseDto> responseDtoList = new ArrayList<>();
        seatReservationList.forEach(sr -> responseDtoList.add(sr.toDto()));
        return responseDtoList;


    }

    @Override
    public MemberSeatReservationResponseDto getMemberSeatReservation(Long seatReservationId) {
        Optional<MemberSeatReservation> memberSeatReservation = memberSeatReservationRepository.findBySeatReservationId(seatReservationId);
        return memberSeatReservation.isPresent() ? memberSeatReservation.get().toDto() : null;
    }

    @Override
    public int calPrice(CalPriceRequsetDto calPriceRequsetDto) {
        int price = calPriceRequsetDto.getPrice();
        String grade = calPriceRequsetDto.getGrade();
        Double couponDiscount = calPriceRequsetDto.getCouponDiscount();

        if (couponDiscount != null) {
            switch (grade) {
                case "VIP":
                    return (int) ((price * 1.2) * (1 - couponDiscount));
                case "Normal":
                    return (int) ((price * (1 - couponDiscount)));
            }
        } else {
            switch (grade) {
                case "VIP":
                    return (int) (price * 1.2);
    
                case "Normal":
                    return price;
                    
            }
        }
        return price;
        
    }

    @Override
    public List<SeatReservationResponseDto> getSeatReservationListByMember(Long memberId) {
        List<MemberSeatReservation> memberSeatReservationList = memberSeatReservationRepository.findByMemberId(memberId);
        List<SeatReservationResponseDto> responseDtoList = new ArrayList<>();
        memberSeatReservationList.forEach(msr -> responseDtoList.add(msr.getSeatReservation().toDto()));
        return responseDtoList;
    }
}
