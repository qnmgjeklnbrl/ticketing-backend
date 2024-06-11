package ticketing.ticket.performance.domain.dto;

import lombok.Data;
import ticketing.ticket.performance.domain.entity.Performance;

import java.util.*;

@Data
public class PerformanceDto {
    private Long performanceId;
    private String name;
    
    private List<PerformanceDetailDto> performanceDetailDtoList = new ArrayList<>();

    public Performance toEntity() {
        Performance performance = new Performance();
        performance.setPerformanceId(this.performanceId);
        performance.setName(this.name);
        // PerformanceDetailDto 리스트를 엔티티로 변환하는 로직이 필요하다면 여기에 추가
        return performance;
    }
}