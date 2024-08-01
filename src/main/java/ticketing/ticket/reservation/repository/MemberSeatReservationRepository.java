package ticketing.ticket.reservation.repository;

import ticketing.ticket.reservation.domain.entity.MemberSeatReservation;
import java.util.Optional;
import java.util.List;

public interface MemberSeatReservationRepository {
    void save(MemberSeatReservation memberSeatReservation);
    Optional<MemberSeatReservation> findBySeatReservationId(Long seatReservationId);
    List<MemberSeatReservation> findByMemberId(Long memberId);
}
