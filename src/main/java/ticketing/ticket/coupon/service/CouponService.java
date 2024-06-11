package ticketing.ticket.coupon.service;

import ticketing.ticket.coupon.domain.dto.CouponDto;
import java.util.*;


public interface CouponService {
    void setCoupon(CouponDto couponDtopon);
    List<CouponDto> getAllCoupons();
}
