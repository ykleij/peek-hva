package com.example.backend.repositories;

import com.example.backend.models.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class ClientRepository implements GenericRepository<Client> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Client> findAll() {
        TypedQuery<Client> query = this.em.createQuery(
                "SELECT c from Client c", Client.class);
        return query.getResultList();
    }

    @Override
    public Client findById(long id) {
        return em.find(Client.class, id);
    }

    @Override
    public Client save(Client client) {
        if (client.getId() == 0) {
            return em.merge(client);
        } else {
            em.persist(client);
            return client;
        }
    }

    @Override
    public Client deleteById(long id) {
        Client client = this.findById(id);
        em.remove(client);
        return client;
    }
}
