package com.example.backend.rest;

import com.example.backend.dto.MatchHistoryDTO;
import com.example.backend.dto.MatchResult;
import com.example.backend.models.MatchHistory;
import com.example.backend.models.MatchedExpert;
import com.example.backend.repositories.MatchHistoryRepository;
import com.example.backend.repositories.ProjectRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/matching")
public class MatchingController {

    @Autowired
    MatchingService matchingService;

    @Autowired
    MatchHistoryRepository matchHistoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    @GetMapping
    public List<MatchHistory> findAll() {
        return this.matchHistoryRepository.findAll();
    }

    @Transactional
    @GetMapping("/project/{projectId}/users")
    public ResponseEntity<List<MatchResult>> matchProjectWithExperts(@PathVariable long projectId, @RequestParam long adminUserId) {
        List<MatchResult> matchResults = matchingService.getProjectMatchingResults(projectId);
//        System.out.println(matchResults);

        Date initiationDate = new Date();

        MatchHistory matchHistory = new MatchHistory();
        matchHistory.setAdmin(userRepository.findById(adminUserId));
        matchHistory.setProject(projectRepository.findById(projectId));

        matchHistory.setMatchedExperts(
                matchResults.stream()
                        .map(matchResult -> {
                            MatchedExpert matchedExpert = new MatchedExpert();
                            matchedExpert.setMatchHistory(matchHistory);
                            matchedExpert.setUser(matchResult.getUser());
                            matchedExpert.setMatchingScore(matchResult.getMatchingScore());
                            return matchedExpert;
                        })
                        .collect(Collectors.toList())
        );

        matchHistory.setDateTimeMatched(initiationDate);

        matchHistoryRepository.save(matchHistory);

        return ResponseEntity.ok(matchResults);
    }

    @GetMapping("/project/{projectId}/match-history")
    public ResponseEntity<List<MatchHistoryDTO>> getMatchHistoryByProjectId(@PathVariable long projectId) {
        return ResponseEntity.ok(matchHistoryRepository.findByProjectId(projectId));
    }

    @GetMapping("/match-history")
    public ResponseEntity<List<MatchResult>> getMatchesByMatchId(@RequestParam long matchId) {
        return ResponseEntity.ok(matchHistoryRepository.findMatchResultsByMatchId(matchId));
    }

}
