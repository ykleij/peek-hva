package com.example.backend.repositories;

import com.example.backend.dto.MatchHistoryDTO;
import com.example.backend.dto.MatchResult;
import com.example.backend.models.MatchHistory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class MatchHistoryRepository implements GenericRepository<MatchHistory> {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<MatchHistory> findAll() {
        TypedQuery<MatchHistory> query = this.em.createQuery("SELECT mh FROM MatchHistory mh", MatchHistory.class);
        return query.getResultList();
    }

    @Override
    public MatchHistory findById(long id) {
        return em.find(MatchHistory.class, id);
    }

    public List<MatchHistoryDTO> findByProjectId(long projectId) {
        String sqlQuery = "SELECT mh.id, u.id, CONCAT(up.first_name, ' ', up.last_name) as adminName, mh.date_time_matched, " +
                "(SELECT COUNT(*) FROM matched_experts me WHERE me.match_history_id = mh.id) as matchedExpertsCount " +
                "FROM match_history mh " +
                "INNER JOIN user u ON mh.admin_user_id = u.id " +
                "INNER JOIN user_profile up ON u.user_profile_id = up.id " +
                "WHERE mh.project_id = :projectId";

        List<Object[]> resultList = em.createNativeQuery(sqlQuery)
                .setParameter("projectId", projectId)
                .getResultList();

        List<MatchHistoryDTO> dtoList = new ArrayList<>();

        for (Object[] row : resultList) {
            MatchHistoryDTO dto = new MatchHistoryDTO();
            dto.setId((Long) row[0]);
            dto.setAdminId((Long) row[1]);
            dto.setAdminName((String) row[2]);
            dto.setDateTimeMatched((Date) row[3]);
            dto.setMatchedExpertsCount((Long) row[4]);
            dtoList.add(dto);
        }

        return dtoList;
    }

    public List<MatchResult> findMatchResultsByMatchId(long matchId) {
        TypedQuery<MatchResult> query = this.em.createQuery(
                "SELECT new com.example.backend.dto.MatchResult(u, me.matchingScore) " +
                        "FROM MatchHistory mh " +
                        "JOIN mh.matchedExperts me " +
                        "JOIN me.user u " +
                        "WHERE mh.id = :matchId",
                MatchResult.class
        );
        query.setParameter("matchId", matchId);

        return query.getResultList();
    }


    @Override
    public MatchHistory save(MatchHistory entity) {
        return em.merge(entity);
    }

    @Override
    public MatchHistory deleteById(long id) {
        MatchHistory matchHistory = this.findById(id);
        em.remove(matchHistory);
        return matchHistory;
    }
}
