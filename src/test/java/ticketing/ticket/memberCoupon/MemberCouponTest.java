package ticketing.ticket.memberCoupon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;

import ticketing.ticket.member.domain.dto.MemberCouponRequestDto;

import ticketing.ticket.member.service.MemberCouponService;

@SpringBootTest
@Transactional
public class MemberCouponTest {
 
    

    @Autowired
    private  MemberCouponService memberCouponService;
    

    @Test
    void pessimisticLockTest () throws InterruptedException {

         // given
         ExecutorService executorService = Executors.newFixedThreadPool(5);
         CountDownLatch countDownLatch = new CountDownLatch(5);
         List<MemberCouponRequestDto> dtos = new ArrayList<>();
         for (int i = 1; i <= 5; i++) {
            MemberCouponRequestDto dto = new MemberCouponRequestDto();
            dto.setMemberId((long) i+8);
            dto.setCouponId(6L);
            dtos.add(dto);
         }


          //when
        
        for ( int i = 0; i < 5; i++) {
            MemberCouponRequestDto memberCouponRequestDto = dtos.get(i);
           
            executorService.execute(()->{
                try {
                    memberCouponService.saveMemberCoupon(memberCouponRequestDto);
                } catch (Exception e) { 
                    System.out.println(e);
                    System.out.println("에러");
                }

                
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
    }

    
}
