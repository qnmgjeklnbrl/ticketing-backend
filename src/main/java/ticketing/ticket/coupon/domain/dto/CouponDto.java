package ticketing.ticket.coupon.domain.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.Data;
import ticketing.ticket.coupon.domain.entity.Coupon;
import ticketing.ticket.member.domain.entity.MemberCoupon;

@Data
public class CouponDto {
    
    private Long couponId;

    private String name;

    private Double percent;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;

    private Long quantity;

    private List<MemberCoupon> memberCoupons = new ArrayList<>();

      // toEntity 메소드 추가
    public Coupon toEntity() {
        Coupon coupon = new Coupon();
        coupon.setCouponId(this.couponId);
        coupon.setName(this.name);
        coupon.setPercent(this.percent);
        coupon.setEndDate(this.endDate);
        coupon.setQuantity(this.quantity);
        // MemberCoupon 리스트는 엔티티 변환 시에는 포함하지 않음
        // 관계 설정이 필요한 경우, 해당 엔티티의 서비스 레이어에서 처리하는 것을 고려해야 함

        return coupon;
    }
}
