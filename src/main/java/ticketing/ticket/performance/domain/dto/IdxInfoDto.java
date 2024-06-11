package ticketing.ticket.performance.domain.dto;

import lombok.Builder;
import lombok.Getter;


@Builder@Getter
public class IdxInfoDto {
    private Long maxIdx;
    private Long minIdx;

}
