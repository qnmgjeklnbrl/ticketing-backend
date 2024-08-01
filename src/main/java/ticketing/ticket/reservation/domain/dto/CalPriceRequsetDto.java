package ticketing.ticket.reservation.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CalPriceRequsetDto {
    private Integer price;
    private String grade;
    private Double couponDiscount;
}
