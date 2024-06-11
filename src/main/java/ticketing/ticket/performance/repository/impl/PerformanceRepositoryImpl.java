package ticketing.ticket.performance.repository.impl;



import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ticketing.ticket.performance.domain.entity.Performance;
import ticketing.ticket.performance.domain.entity.PerformanceDetail;
import ticketing.ticket.performance.repository.PerformanceRepository;

@Repository
public class PerformanceRepositoryImpl implements PerformanceRepository{
    @PersistenceContext
    private  EntityManager em;

    // 공연 카테고리 저장, 업데이트
    @Override
    public void save(Performance performance) {
        
        if (performance.getPerformanceId() == null) {
            em.persist(performance);
        } else {
            em.merge(performance);
        }
    }
    // 공연카테고리 단건 조회
    @Override
    public Performance findById(Long performanceId) {
       return em.find(Performance.class, performanceId);
    }
    // 모두 조회
    // fetch join안쓰면 n+1문제 
    @Override
    public List<Performance> findAll() {
       return em.createQuery("select p from Performance p ",Performance.class).getResultList();
    }
    // 공연카테고리 단건 삭제
    @Override
    public void deleteById(Long performanceId) {
        Performance performance = findById(performanceId);
        if (performance != null) {
            //연관관계 끊기
            for (PerformanceDetail pd : performance.getPerformanceDetailList()) {
                pd.setPerformance(null);
            }
            em.remove(performance);
        }
    }
    

    
    
    
}
