package ticketing.ticket.performance.repository.impl;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import ticketing.ticket.performance.domain.dto.PerfSearchDto;
import ticketing.ticket.performance.domain.entity.PerformanceDetail;
import ticketing.ticket.performance.domain.entity.QPerformanceDetail;
import ticketing.ticket.performance.repository.PerformanceDetailRepository;
import static ticketing.ticket.performance.domain.entity.QPerformanceDetail.performanceDetail;

@Repository
public class PerformanceDetailRepositoryImpl implements PerformanceDetailRepository {

    @PersistenceContext
    private  EntityManager em;
    // 공연 디테일 저장, 업데이트
    @Override
    public void save(PerformanceDetail performanceDetail) {
        if (performanceDetail.getPerformanceDetailId() == null) {
            em.persist(performanceDetail);
        } else {
            em.merge(performanceDetail);
        }
    }
    // id 단건 조회
    @Override
    public PerformanceDetail findById(Long performanceDetailId) {
        return em.find(PerformanceDetail.class, performanceDetailId);
    }
    // 카테고리별 조회
    @Override
    public List<PerformanceDetail> findByPerformanceId(PerfSearchDto perfSearchDto) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);


        List<PerformanceDetail> results = queryFactory
            .selectFrom(performanceDetail)
            .leftJoin(performanceDetail.performance)
            .fetchJoin()
            .where(
                eqPerfId(perfSearchDto.getPerfId()),
                selectByIdxAndBtn(perfSearchDto.getIndex(), perfSearchDto.getPerfId(), perfSearchDto.getButton()),
                eqTitle(perfSearchDto.getTitle())
            )
            .orderBy(performanceDetail.performanceDetailId.desc())
            .limit(perfSearchDto.getSize())
            .fetch();
        results.forEach(System.out::println );
        return results;

        
    }

    private BooleanExpression eqPerfId(Long perfId){
        if (perfId == null) {
            return null;
        } else {
            return performanceDetail.performance.performanceId.eq(perfId);
        }
   }

   private BooleanExpression selectByIdxAndBtn(Integer idx, Long perfId, String button){
        if (idx == null) {
        
           Long maxidx =findMaxIdByPerformanceId(perfId)+1;
           System.out.println("maxidx : " + maxidx);
           System.out.println(performanceDetail.performanceDetailId.lt(maxidx));
           return performanceDetail.performanceDetailId.lt(maxidx);
           
        } else {
            if (button.equals("next")) {
                
                return performanceDetail.performanceDetailId.lt(Long.valueOf(idx));
            } else {
                
                return performanceDetail.performanceDetailId.gt(Long.valueOf(idx));
            }
        }
   }

   private BooleanExpression eqTitle(String title){
        if (title == null) {
            return null;
        } else {
            return performanceDetail.artist.like("%" + title + "%");
        }
   }


 
   
    // 모두 조회
    @Override
    public List<PerformanceDetail> findAll() {
        return em.createQuery("select p from PerformanceDetail p", PerformanceDetail.class).getResultList();
    }

    //id 단건 삭제
    @Override
    public void deleteById(Long performanceDetailId) {
        PerformanceDetail performanceDetail = findById(performanceDetailId);
        if (performanceDetail != null) {
            em.remove(performanceDetail);
        }
    }
    @Override
    public Long findMaxIdByPerformanceId(Long performanceId) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> query = cb.createQuery(Long.class);
    Root<PerformanceDetail> root = query.from(PerformanceDetail.class);

    query.select(cb.max(root.get("id")));

    if (performanceId != null) {
        query.where(cb.equal(root.get("performance").get("id"), performanceId));
    }

    return em.createQuery(query).getSingleResult();
    }   
    @Override
    public Long findMinIdByPerformanceId(Long performanceId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<PerformanceDetail> root = query.from(PerformanceDetail.class);

        query.select(cb.min(root.get("id")));  // 최소 ID를 선택

        if (performanceId != null) {
            query.where(cb.equal(root.get("performance").get("id"), performanceId));
        }

        return em.createQuery(query).getSingleResult();
    }
    @Override
    public Long findMaxId() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<PerformanceDetail> root = query.from(PerformanceDetail.class);
        query.select(cb.max(root.get("id")));
        return em.createQuery(query).getSingleResult();
    }
    @Override
    public Long findMinId() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<PerformanceDetail> root = query.from(PerformanceDetail.class);
        query.select(cb.min(root.get("id")));
        return em.createQuery(query).getSingleResult();
    }
    
}
