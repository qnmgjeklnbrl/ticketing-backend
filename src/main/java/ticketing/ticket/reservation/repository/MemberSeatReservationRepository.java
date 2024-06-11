package ticketing.ticket.reservation.repository;

import ticketing.ticket.reservation.domain.entity.MemberSeatReservation;
import java.util.Optional;

public interface MemberSeatReservationRepository {
    void save(MemberSeatReservation memberSeatReservation);
    Optional<MemberSeatReservation> findBySeatReservationId(Long seatReservationId);
}
