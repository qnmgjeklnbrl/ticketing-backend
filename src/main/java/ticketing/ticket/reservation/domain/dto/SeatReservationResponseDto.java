package ticketing.ticket.reservation.domain.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatReservationResponseDto {
   
    private Long seatReservationId;

    private String seatName;
  
    private Long performDetailId;

    private boolean available;

    private String grade;
    

   
}
