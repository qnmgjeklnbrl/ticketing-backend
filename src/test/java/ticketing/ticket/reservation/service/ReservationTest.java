package ticketing.ticket.reservation.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;



import jakarta.transaction.Transactional;
import ticketing.ticket.reservation.domain.dto.ReservationRequestDto;

@SpringBootTest
@Transactional
public class ReservationTest {
    @Autowired
    private ReservationService reservationService;

    AtomicBoolean isDuplicated = new AtomicBoolean();
    @Test
    @DisplayName("좌석 예약 동시성 테스트")
    void threadSafeTest() throws InterruptedException {
        
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<ReservationRequestDto> dtos = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            ReservationRequestDto  requestDto = new ReservationRequestDto();
            requestDto.setMemberId((long) i);
            requestDto.setSeatReservationId(1469L); 
            dtos.add(requestDto);
        }

        //when
        
        for ( int i = 0; i < 5; i++) {
            ReservationRequestDto requestDto = dtos.get(i);
            executorService.execute(()->{
                try {
                    reservationService.setReservation(requestDto);
                } catch (ObjectOptimisticLockingFailureException e) { //중복 발생
                    isDuplicated.set(true);
                    System.out.println("중복발생");
                }

                
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();

        //then 
        assertTrue(isDuplicated.get());
    }
}
