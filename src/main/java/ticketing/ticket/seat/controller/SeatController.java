package ticketing.ticket.seat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ticketing.ticket.seat.service.SeatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/seat")
public class SeatController {
    private final SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService){
        this.seatService = seatService;
    }
    @Secured("ROLE_ADMIN")
    @PostMapping("/all")
    public ResponseEntity<Void> setAllseat(
            @RequestParam("row") int row, 
            @RequestParam("col") int col ) {

        seatService.setAllSeat(row, col);
        return ResponseEntity.ok().build();
        
    }
    
}
