package ticketing.ticket.reservation.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data@Builder
public class MemberSeatReservationResponseDto {
    private Long memberSeatReservationId;

    private Integer totalPrice;

    private String memberName;

    private String memberEamil;

}
