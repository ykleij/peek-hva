package com.example.backend.repositories;

import com.example.backend.models.ProjectRole;
import com.example.backend.models.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class RoleRepository implements GenericRepository<Role> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Role> findAll() {
        TypedQuery<Role> query = this.em.createQuery("SELECT r from Role r", Role.class);
        return query.getResultList();
    }

    @Override
    public Role findById(long id) {
        return em.find(Role.class, id);
    }

    public Role findByNameOrSave(String roleName, boolean inList) {
        try {
            Role role = this.em.createQuery("SELECT r from Role r WHERE r.roleName = ?1", Role.class)
                    .setParameter(1, roleName)
                    .getSingleResult();

            return role;
        } catch (NoResultException ex) {
            return em.merge(new Role(roleName, inList));
        }
    }

    public Role findByIdAndUpdate(long id, String newRoleName, boolean newInList) {
        Role role = findById(id);
        if (role != null) {
            role.setRoleName(newRoleName);
            role.setInList(newInList);
            em.merge(role);
        }
        return role;
    }


    @Override
    public Role save(Role role) {
        return em.merge(role);
    }

    @Override
    public Role deleteById(long id) {
        Role role = this.findById(id);
        em.remove(role);
        return role;
    }
}
