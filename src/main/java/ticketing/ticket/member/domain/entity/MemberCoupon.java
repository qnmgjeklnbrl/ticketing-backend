package ticketing.ticket.member.domain.entity;

import org.springframework.boot.context.properties.bind.DefaultValue;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;
import ticketing.ticket.base.BaseEntity;
import ticketing.ticket.coupon.domain.entity.Coupon;

@Entity
@Data
@ToString(exclude = "coupon")
public class MemberCoupon extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberCouponId;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
    
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isUsed;
}