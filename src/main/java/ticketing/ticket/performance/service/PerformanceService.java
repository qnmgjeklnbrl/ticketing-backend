package ticketing.ticket.performance.service;

import ticketing.ticket.performance.domain.dto.PerformanceDto;
import java.util.List;

public interface PerformanceService {
    void setPerformance(PerformanceDto PerformanceDto);

    PerformanceDto getPerformance(Long PerformanceId);

    List<PerformanceDto> getAllPerformance();

    void deletePerformance(Long PerformanceId);
}
