package ticketing.ticket.seat.repository.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import ticketing.ticket.seat.domain.entity.Seat;
import ticketing.ticket.seat.repository.SeatRepository;
@Repository
@RequiredArgsConstructor
public class SeatRepositoryImpl implements SeatRepository {
    @PersistenceContext
    private  EntityManager em;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Seat seat) {
        if (seat.getSeatId() == null) {
            em.persist(seat);
        } else {
            em.merge(seat);
        }
    }
 
    
    @Override
    public void bulkInsert(List<Seat> list) {
        String sql = "INSERT INTO seat "
            + "(name, grade, updated_at, created_at) VALUES (?,?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
           @Override
           public void setValues(PreparedStatement ps, int i) throws SQLException {
               Seat seat = list.get(i);
               ps.setString(1, seat.getName());
               ps.setString(2, seat.getGrade());
               ps.setObject(3, LocalDateTime.now());
               ps.setObject(4, LocalDateTime.now());
           }

            @Override
            public int getBatchSize() {
                return list.size();
            } 
        
        });
    }
    //id 단건 조회
    @Override
    public Seat findbyId(Long SeatId) {
       return em.find(Seat.class, SeatId);
    }

    @Override
    public List<Seat> findAll() {
        return em.createQuery("select s from Seat s",Seat.class).getResultList();
    }
    
}
