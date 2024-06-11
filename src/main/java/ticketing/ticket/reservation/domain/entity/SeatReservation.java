package ticketing.ticket.reservation.domain.entity;

import java.util.Iterator;

import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import lombok.Data;
import ticketing.ticket.base.BaseEntity;


import ticketing.ticket.performance.domain.entity.PerformanceDetail;
import ticketing.ticket.reservation.domain.dto.SeatReservationResponseDto;
import ticketing.ticket.seat.domain.entity.Seat;

@Entity
@Data
public class SeatReservation extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatReservationId;

   

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "performance_detail_id")
    private PerformanceDetail performanceDetail;

    private boolean available;

    @Version
    private Long version;
   
    public SeatReservationResponseDto toDto(){
        return SeatReservationResponseDto.builder()
        .seatReservationId(this.seatReservationId)
        .seatName(this.seat.getName())
        .available(this.available)
        .performDetailId(this.performanceDetail.getPerformanceDetailId())
        .grade(this.seat.getGrade())
        .build();
    }
    
    
}