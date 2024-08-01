package ticketing.ticket.reservation.service;

import java.util.List;

import ticketing.ticket.reservation.domain.dto.CalPriceRequsetDto;
import ticketing.ticket.reservation.domain.dto.MemberSeatReservationResponseDto;
import ticketing.ticket.reservation.domain.dto.ReservationRequestDto;
import ticketing.ticket.reservation.domain.dto.SeatReservationResponseDto;


public interface ReservationService {
    void setReservation(ReservationRequestDto reservationRequestDto);

    List<SeatReservationResponseDto> getSeatReservationListByPerformanceDetail(Long performDetailId);

    List<SeatReservationResponseDto> getSeatReservationListByMember(Long memberId);

    MemberSeatReservationResponseDto getMemberSeatReservation(Long seatReservationId);

    int calPrice(CalPriceRequsetDto calPriceRequsetDto);
}
