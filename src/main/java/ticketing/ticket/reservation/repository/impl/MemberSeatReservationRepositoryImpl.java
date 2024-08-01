package ticketing.ticket.reservation.repository.impl;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.Optional; 
import ticketing.ticket.reservation.domain.entity.MemberSeatReservation;
import ticketing.ticket.reservation.repository.MemberSeatReservationRepository;
import java.util.List;
@Repository
public class MemberSeatReservationRepositoryImpl implements MemberSeatReservationRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(MemberSeatReservation memberSeatReservation) {
        
        if (memberSeatReservation.getMemberSeatReservationId() == null) {
            em.persist(memberSeatReservation);
        } else {
            em.merge(memberSeatReservation);
        }
        
    }

    @Override
    public Optional<MemberSeatReservation> findBySeatReservationId(Long seatReservationId) {
        TypedQuery<MemberSeatReservation> query = em.createQuery("SELECT msr FROM MemberSeatReservation msr JOIN FETCH msr.member WHERE msr.seatReservation.seatReservationId = :seatReservationId", MemberSeatReservation.class);
        query.setParameter("seatReservationId", seatReservationId);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<MemberSeatReservation> findByMemberId(Long memberId) {
        TypedQuery<MemberSeatReservation> query = em.createQuery("SELECT msr FROM MemberSeatReservation msr JOIN FETCH msr.seatReservation sr JOIN FETCH sr.performanceDetail pd JOIN FETCH sr.seat s WHERE msr.member.id = :memberId", MemberSeatReservation.class);
        query.setParameter("memberId", memberId);
        return query.getResultList();
    }
}
