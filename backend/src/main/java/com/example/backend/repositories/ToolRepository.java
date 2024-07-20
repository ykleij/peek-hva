package com.example.backend.repositories;

import com.example.backend.models.Tool;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ToolRepository implements GenericRepository<Tool> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Tool> findAll() {
        TypedQuery<Tool> query = this.em.createQuery("SELECT t from Tool t", Tool.class);
        return query.getResultList();
    }

    @Override
    public Tool findById(long id) {
        return em.find(Tool.class, id);
    }

    public Tool findByNameOrSave(String toolName, boolean inList) {
        try {
            Tool tool = this.em.createQuery("SELECT t from Tool t WHERE t.toolName = ?1", Tool.class)
                    .setParameter(1, toolName)
                    .getSingleResult();

            return tool;
        } catch (NoResultException ex) {
            return em.merge(new Tool(toolName, inList));
        }
    }

    public Tool findByIdAndUpdate(long id, String newToolName, boolean newInList) {
        Tool tool = findById(id);
        if (tool != null) {
            tool.setToolName(newToolName);
            tool.setInList(newInList);
            em.merge(tool);
        }
        return tool;
    }

    @Override
    public Tool save(Tool tool) {
        return em.merge(tool);
    }

    @Override
    public Tool deleteById(long id) {
        Tool tool = this.findById(id);
        em.remove(tool);
        return tool;
    }
}
