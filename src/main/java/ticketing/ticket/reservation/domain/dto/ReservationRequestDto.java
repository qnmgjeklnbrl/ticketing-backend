package ticketing.ticket.reservation.domain.dto;

import lombok.Data;

@Data
public class ReservationRequestDto {
    
    private Long seatReservationId;
    private Long memberId;
    private Long memberCouponId;
    private Integer totalPrice;
    
    
}
