package ticketing.ticket.performance.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerfSearchDto {
    private Long perfId;
    private String title;
    private String button;
    private Integer index;
    private Integer size;
}
