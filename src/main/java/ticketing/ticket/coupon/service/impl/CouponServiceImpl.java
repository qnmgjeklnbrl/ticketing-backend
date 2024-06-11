package ticketing.ticket.coupon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ticketing.ticket.coupon.domain.dto.CouponDto;
import ticketing.ticket.coupon.domain.entity.Coupon;
import ticketing.ticket.coupon.repository.CouponRepository;
import ticketing.ticket.coupon.service.CouponService;
import java.util.*;
@Service
@Transactional
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;

    @Autowired
    public CouponServiceImpl(CouponRepository couponRepository){
        this.couponRepository = couponRepository;
    }

    @Override
    public void setCoupon(CouponDto couponDto) {
        Coupon coupon = couponDto.toEntity();
        couponRepository.save(coupon);
    }

    @Override
    public List<CouponDto> getAllCoupons() {
        List<Coupon> coupons = couponRepository.findAll();
        List<CouponDto> couponDtos = new ArrayList<>();
        coupons.forEach(c->{
            couponDtos.add(c.toDto());
        });
        return couponDtos;

   }
    
}
