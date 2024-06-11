package ticketing.ticket.coupon.repository;

import java.util.*;


import ticketing.ticket.coupon.domain.entity.Coupon;
public interface CouponRepository {
    void save(Coupon coupon);
    List<Coupon> findAll();
    
    Coupon findById(Long id);
}
