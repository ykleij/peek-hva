package com.example.backend.repositories;

import com.example.backend.models.ResetPassword;
import com.example.backend.models.User;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
@Transactional
public class ResetPasswordRepository implements GenericRepository<ResetPassword>{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ResetPassword> findAll() {
        Query query = this.em.createQuery(
                "SELECT rp FROM ResetPassword rp");
        return query.getResultList();
    }

    public ResetPassword findByUrl(String url) {
        try {
            ResetPassword resetPassword = em.createQuery(
                    "SELECT rp FROM ResetPassword rp WHERE rp.url = :url", ResetPassword.class)
                    .setParameter("url", url)
                    .getSingleResult();
            return resetPassword;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public ResetPassword findById(long id) {
        return em.find(ResetPassword.class, id);
    }


    @Override
    @Transactional
    public ResetPassword save(ResetPassword resetPassword) {
        return em.merge(resetPassword);
    }

    @Override
    public ResetPassword deleteById(long id) {
        ResetPassword resetPassword = this.findById(id);
        em.remove(resetPassword);
        return resetPassword;
    }
}
