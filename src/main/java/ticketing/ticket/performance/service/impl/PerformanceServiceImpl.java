package ticketing.ticket.performance.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ticketing.ticket.performance.domain.dto.PerformanceDetailDto;
import ticketing.ticket.performance.domain.dto.PerformanceDto;
import ticketing.ticket.performance.domain.entity.Performance;
import ticketing.ticket.performance.repository.PerformanceRepository;

import ticketing.ticket.performance.service.PerformanceService;
@Service
@Transactional
public class PerformanceServiceImpl implements PerformanceService {
    private final PerformanceRepository performanceRepository;

    @Autowired
    public PerformanceServiceImpl(PerformanceRepository performanceRepository){
        this.performanceRepository = performanceRepository;
    }
    // 공연 카테고리 저장
    @Override
    public void setPerformance(PerformanceDto PerformanceDto) {
       
        Performance performance = new Performance();
        performance.setName(PerformanceDto.getName());
        performanceRepository.save(performance);
    }
    // 단일 공연 카테고리 조회
    @Override
    public PerformanceDto getPerformance(Long PerformanceId) {
       Performance performance= performanceRepository.findById(PerformanceId);
       PerformanceDto performanceDto = new PerformanceDto();
       performanceDto.setPerformanceId(performance.getPerformanceId());
       performanceDto.setName(performance.getName());
       return performanceDto;
    }
    // 모든 공연 카테고리 조회
    @Override
    public List<PerformanceDto> getAllPerformance() {
        List<Performance> performanceList = performanceRepository.findAll();
        List<PerformanceDto> dtoList = new ArrayList<>();
            performanceList.forEach(p->{
                dtoList.add(p.toDto());
        });
        
    
        return dtoList;
    }
    // 공연 카테고리 삭제
    @Override
    public void deletePerformance(Long PerformanceId) {
        performanceRepository.deleteById(PerformanceId);
    }
    
    


}
