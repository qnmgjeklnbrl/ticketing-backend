package ticketing.ticket.reservation.repository.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import ticketing.ticket.reservation.domain.entity.SeatReservation;
import ticketing.ticket.reservation.repository.SeatReservationRepository;
@Repository
@RequiredArgsConstructor
public class SeatReservationRepositoryImpl implements SeatReservationRepository {

    @PersistenceContext
    private EntityManager em;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(SeatReservation seatReservation) {
        if (seatReservation.getSeatReservationId() == null) {
            em.persist(seatReservation);
        } else {
            em.merge(seatReservation);
            em.flush();
        }
    }

    @Override
    public void bulkInsert(List<SeatReservation> seatReservationlist) {
        String sql = "INSERT INTO seat_reservation "
            + "(seat_id, performance_detail_id, available, version, updated_at, created_At) VALUES (?,?,?,0,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                SeatReservation seatReservation = seatReservationlist.get(i);
                ps.setLong(1, seatReservation.getSeat().getSeatId());
                ps.setLong(2, seatReservation.getPerformanceDetail().getPerformanceDetailId());
                ps.setBoolean(3, true);
                ps.setObject(4, LocalDateTime.now());
                ps.setObject(5, LocalDateTime.now());

            }
            @Override
            public int getBatchSize() {
                
                return seatReservationlist.size();
            }
        });
        System.out.println("SeatReservation 벌크 인서트");
    }
    
    @Override
    public SeatReservation findById(Long seatReservationId) {
        return em.find(SeatReservation.class, seatReservationId);
    }

    @Override
    public List<SeatReservation> findByperformDetailId(Long perfDetailId) {
        TypedQuery<SeatReservation> query = em.createQuery(
            "SELECT  sr FROM SeatReservation sr JOIN FETCH sr.performanceDetail JOIN FETCH sr.seat WHERE sr.performanceDetail.performanceDetailId = :perfDetailId",
            SeatReservation.class
        );
        query.setParameter("perfDetailId", perfDetailId);
       
        return query.getResultList();
    }
}

