package ticketing.ticket.seat.repository;

import java.util.*;

import ticketing.ticket.seat.domain.entity.Seat;
public interface SeatRepository {
    void save(Seat seat);
    void bulkInsert(List<Seat> list);
    Seat findbyId(Long SeatId);
    List<Seat>findAll();
}
