package ticketing.ticket.performance.repository;
import ticketing.ticket.performance.domain.entity.Performance;
import java.util.List;

public interface PerformanceRepository {
    void save(Performance performance);

    Performance findById(Long performanceId);
    
    List<Performance> findAll();

    void deleteById(Long performanceId);



    

    
}
