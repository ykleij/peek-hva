package com.example.backend.repositories;

import com.example.backend.models.Sector;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class SectorRepository implements GenericRepository<Sector> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Sector> findAll() {
        TypedQuery<Sector> query = this.em.createQuery("SELECT s from Sector s", Sector.class);
        return query.getResultList();
    }

    @Override
    public Sector findById(long id) {
        return em.find(Sector.class, id);
    }

    public Sector findByNameOrSave(String sectorName, boolean inList) {
        try {
            Sector sector = this.em.createQuery("SELECT s FROM Sector s WHERE s.sectorName = ?1", Sector.class)
                    .setParameter(1, sectorName)
                    .getSingleResult();

            return sector;
        } catch (NoResultException ex) {
            return em.merge(new Sector(sectorName, inList));
        }
    }

    public Sector findByIdAndUpdate(long id, String newSectorName, boolean newInList) {
        Sector sector = findById(id);
        if (sector != null) {
            sector.setSectorName(newSectorName);
            sector.setInList(newInList);
            em.merge(sector);
        }
        return sector;
    }

    @Override
    public Sector save(Sector sector) {
        return em.merge(sector);
    }

    @Override
    public Sector deleteById(long id) {
        Sector sector = this.findById(id);
        em.remove(sector);
        return sector;
    }
}
