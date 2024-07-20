package com.example.backend.repositories;

import com.example.backend.models.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CommentRepository implements GenericRepository<Comment>{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Comment> findAll() {
        Query query = this.em.createQuery(
                "SELECT ep FROM Comment ep");
        return query.getResultList();
    }

    @Override
    public Comment findById(long id) {
        return em.find(Comment.class, id);
    }

    public List<Comment> findByPostedOnId(long id) { Query query = this.em.createQuery(
            "SELECT ep FROM Comment ep WHERE ep.postedOn.id = :id ORDER BY ep.dateCreated DESC");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public Comment save(Comment entity) {
        return em.merge(entity);
    }


    @Override
    public Comment deleteById(long id) {
        Comment comment = this.findById(id);
        em.remove(comment);
        return comment;
    }
}