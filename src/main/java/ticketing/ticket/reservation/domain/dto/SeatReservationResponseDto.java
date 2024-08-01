package ticketing.ticket.reservation.domain.dto;


import lombok.Builder;
import lombok.Data;
import ticketing.ticket.performance.domain.dto.PerformanceDetailDto;
import java.util.List;

@Data
@Builder
public class SeatReservationResponseDto {
   
    private Long seatReservationId;

    private String seatName;
  
    private Long performDetailId;

    private boolean available;

    private String grade;

    private PerformanceDetailDto performanceDetail;
    

   
}
