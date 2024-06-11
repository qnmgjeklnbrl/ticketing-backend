package ticketing.ticket.coupon.domain.entity;

import java.time.LocalDate;
import java.util.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import ticketing.ticket.base.BaseEntity;
import ticketing.ticket.coupon.domain.dto.CouponDto;
import ticketing.ticket.member.domain.entity.MemberCoupon;

@Entity
@Data
public class Coupon  extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    private String name;

    private Double percent;

    private LocalDate endDate;

    private Long quantity;

    @OneToMany(mappedBy = "coupon")
    private List<MemberCoupon> memberCoupons = new ArrayList<>();


    public CouponDto toDto() {
        CouponDto dto = new CouponDto();
        dto.setCouponId(this.couponId);
        dto.setName(this.name);
        dto.setPercent(this.percent);
        dto.setEndDate(this.endDate);
        dto.setQuantity(this.quantity);
        // MemberCoupon 리스트는 DTO 변환 시에는 포함하지 않음
        // 해당 정보가 필요한 경우, 별도의 로직을 통해 DTO에 포함시키는 것을 고려

        return dto;
    }
}

