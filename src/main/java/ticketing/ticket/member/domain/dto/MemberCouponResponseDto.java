package ticketing.ticket.member.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MemberCouponResponseDto {
    private Long couponId;
    private String name;
    private Long memberId;
}
