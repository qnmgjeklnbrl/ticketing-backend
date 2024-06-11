package ticketing.ticket.seat.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ticketing.ticket.seat.domain.entity.Seat;
import ticketing.ticket.seat.repository.SeatRepository;
import ticketing.ticket.seat.service.SeatService;

@Service
@Transactional
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    @Autowired
    public SeatServiceImpl(SeatRepository seatRepository){
        this.seatRepository = seatRepository;
    }

    @Override
    public void setAllSeat(int row, int col) {
        List<Seat> list = new ArrayList<>();
        for (int i = 1; i <= row; i++) {
            
            for (int j = 1; j <= col; j++) {
                Seat seat = new Seat();
                int r = 64+i;
                char  cr = (char)r;
                String seatName = Character.toString(cr) + String.valueOf(j);
                if (i == 1) {
                    seat.setGrade("VIP");
                } else{
                    seat.setGrade("Normal");
                }
                
                seat.setName(seatName);
                list.add(seat);
            }
        }
        seatRepository.bulkInsert(list);
    }
    
}
