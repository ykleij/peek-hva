package com.example.backend.repositories;

import com.example.backend.models.ExpertProfile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ExpertProfileRepository implements GenericRepository<ExpertProfile> {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<ExpertProfile> findAll() {
        Query query = this.em.createQuery(
                "SELECT ep FROM ExpertProfile ep JOIN ep.projectRoles pr JOIN pr.roles r");
        return query.getResultList();
    }

    @Override
    public ExpertProfile findById(long id) {
        return em.find(ExpertProfile.class, id);
    }

//    public ExpertProfile findByUserId(long userId) {
//        Query query = this.em.createQuery(
//                "SELECT e FROM ExpertProfile e WHERE e.user.id = :userId");
//        query.setParameter("userId", userId);
//
//        List<ExpertProfile> expertProfile = query.getResultList();
////
////        if (expertProfile.isEmpty()) {
////            ExpertProfile newExpertProfile = new ExpertProfile();
////            newExpertProfile.setUser(userRepository.findById(userId));
////
////            return save(newExpertProfile);
////        }
//
//        return expertProfile.get(0);
//    }

    @Override
    public ExpertProfile save(ExpertProfile entity) {
        return em.merge(entity);
    }


    @Override
    public ExpertProfile deleteById(long id) {
        ExpertProfile expertProfile = this.findById(id);
        em.remove(expertProfile);
        return expertProfile;
    }
}
