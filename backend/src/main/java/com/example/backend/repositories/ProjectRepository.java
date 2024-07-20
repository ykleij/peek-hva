package com.example.backend.repositories;

import com.example.backend.models.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class ProjectRepository implements GenericRepository<Project> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Project> findAll() {
        TypedQuery<Project> query = this.em.createQuery(
                "SELECT p from Project p LEFT JOIN FETCH p.projectRoles", Project.class);
        return query.getResultList();
    }

    public List<Project> getProjectsCreatedBeforeToday() {
        Date currentDate = new Date();

        TypedQuery<Project> query = this.em.createQuery(
                "SELECT p FROM Project p WHERE p.dateCreated < :currentDate ", Project.class
        );
        query.setParameter("currentDate", currentDate);
        return query.getResultList();
    }

    @Override
    public Project findById(long id) {
        return em.find(Project.class, id);
    }

    @Override
    public Project save(Project project) {
        return em.merge(project);
    }

    @Override
    public Project deleteById(long id) {
        Project project = this.findById(id);
        em.remove(project);
        return project;
    }
}
