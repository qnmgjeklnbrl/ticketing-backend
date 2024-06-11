package ticketing.ticket.reservation.repository;

import java.util.List;

import ticketing.ticket.reservation.domain.entity.SeatReservation;

public interface SeatReservationRepository {
    void bulkInsert(List<SeatReservation> seatReservationlist);  
    
    void save(SeatReservation seatReservation);

    SeatReservation findById(Long seatReservationId);
    
    List<SeatReservation> findByperformDetailId(Long performDetailId);
}
