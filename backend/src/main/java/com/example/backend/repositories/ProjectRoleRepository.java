package com.example.backend.repositories;

import com.example.backend.models.ProjectRole;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ProjectRoleRepository implements GenericRepository<ProjectRole> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ProjectRole> findAll() {
        TypedQuery<ProjectRole> query = this.em.createQuery("SELECT pr from ProjectRole pr", ProjectRole.class);
        return query.getResultList();
    }

    @Override
    public ProjectRole findById(long id) {
        return em.find(ProjectRole.class, id);
    }

    @Override
    public ProjectRole save(ProjectRole projectRole) {
        return em.merge(projectRole);
    }

    @Override
    public ProjectRole deleteById(long id) {
        ProjectRole projectRole = this.findById(id);
        em.remove(projectRole);
        return projectRole;
    }
}
