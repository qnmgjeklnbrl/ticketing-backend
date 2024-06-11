package ticketing.ticket.coupon.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import ticketing.ticket.coupon.domain.entity.Coupon;
import ticketing.ticket.coupon.repository.CouponRepository;
@Repository
public class CouponRepositoryImpl implements CouponRepository{
    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Coupon coupon) {
        if (coupon.getCouponId() == null) {
            em.persist(coupon);
        } else {
            em.merge(coupon);
        }
    }

    @Override
    public List<Coupon> findAll() {
        return em.createQuery("select c from Coupon c", Coupon.class).getResultList();
    }

    @Override
    public Coupon findById(Long id) {
        return em.createQuery("select c from Coupon c where c.id = :id ", Coupon.class)
                .setParameter("id", id)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .getSingleResult();
                
    }

    
    
}
