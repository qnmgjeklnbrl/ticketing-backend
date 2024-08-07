package ticketing.ticket.reservation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ticketing.ticket.reservation.domain.dto.SeatReservationResponseDto;
import lombok.RequiredArgsConstructor;
import ticketing.ticket.reservation.domain.dto.CalPriceRequsetDto;
import ticketing.ticket.reservation.domain.dto.MemberSeatReservationResponseDto;
import ticketing.ticket.reservation.domain.dto.ReservationRequestDto;
import ticketing.ticket.reservation.service.ReservationService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;


    @PostMapping("/save")
    public ResponseEntity<Void> setMemberSeatReservaion(@RequestBody ReservationRequestDto requestDto){
        reservationService.setReservation(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all/{perfDetailId}")
    public ResponseEntity<List<SeatReservationResponseDto>> getSeatReservationList(@PathVariable Long perfDetailId){
        List<SeatReservationResponseDto> reservations = reservationService.getSeatReservationListByPerformanceDetail(perfDetailId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/by-seat/{seatReservationId}")
    public ResponseEntity<MemberSeatReservationResponseDto> getMemberSeatReservation(@PathVariable Long seatReservationId){
        MemberSeatReservationResponseDto memberSeatReservationDto = reservationService.getMemberSeatReservation(seatReservationId);
        return ResponseEntity.ok(memberSeatReservationDto);
    }

    @PostMapping("/price")
    public ResponseEntity<Integer> calPrice(@RequestBody CalPriceRequsetDto calPriceRequsetDto) {
        //TODO: process POST request
        int price = reservationService.calPrice(calPriceRequsetDto);
        
        return ResponseEntity.ok(price);
    }
    @GetMapping("/all/by-member/{memberId}")
    public ResponseEntity<List<SeatReservationResponseDto>> getMemberSeatReservationList(@PathVariable Long memberId) {
        List<SeatReservationResponseDto> seatReservationList = reservationService.getSeatReservationListByMember(memberId);
        return ResponseEntity.ok(seatReservationList);
    }
    
    
}

