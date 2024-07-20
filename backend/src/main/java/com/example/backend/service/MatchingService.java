package com.example.backend.service;

import com.example.backend.dto.MatchResult;
import com.example.backend.exceptions.ResourceNotFoundException;
import com.example.backend.models.*;
import com.example.backend.repositories.ExpertProfileRepository;
import com.example.backend.repositories.ProjectRepository;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MatchingService {

    private static double MIN_MATCHING_SCORE = 50;
    private static boolean PRECISE_MATCHING = false;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;

    /**
     * @param projectId
     * @return
     */
    public List<MatchResult> getProjectMatchingResults(Long projectId) {
        Project project;
        List<User> users;

        try {
            project = projectRepository.findById(projectId);
            users = userRepository.findAll();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Project not found.");
        }

        List<MatchResult> matchResults = matchProjectWithExperts(project, users);

        return getTopExpertProfileMatches(matchResults);
    }

    /**
     * @param project
     * @param experts
     * @return
     */
    public List<MatchResult> matchProjectWithExperts(Project project, List<User> users) {
        List<MatchResult> matchResults = new ArrayList<>();
        long projectDetailsCount = countProjectsDetails(project.getProjectRoles());

        for (User user : users) {
            if (user.getExpertProfile() == null) continue;

            double matchingScore = calculateMatchingScore(project.getProjectRoles(), user.getExpertProfile().getProjectRoles(), projectDetailsCount);

            if (matchingScore >= MIN_MATCHING_SCORE) {
                MatchResult matchResult = new MatchResult(user, matchingScore);
                matchResults.add(matchResult);
            }
        }

        return matchResults;
    }

    /**
     * @param projectRoles
     * @param expertRoles
     * @return
     */
    private double calculateMatchingScore(Set<ProjectRole> projectRoles, Set<ProjectRole> expertRoles, long projectDetailsCount) {
        List<Long> totalMatchCount = countCommonalities(projectRoles, expertRoles);
        long highestMatchCount = 0;

        for (long count : totalMatchCount) {
            if (highestMatchCount < count) {
                highestMatchCount = count;
            }
        }

        return (double) highestMatchCount / projectDetailsCount * 100;
    }

    /**
     * @param projectRoles
     * @return
     */
    private long countProjectsDetails(Set<ProjectRole> projectRoles) {
        long sectorCount = 1; // All projects have 1 sector

        long numberOfRolesAndTools = projectRoles.stream()
                .flatMap(projectRole -> Stream.concat(
                        projectRole.getRoles().stream(),
                        projectRole.getTools().stream()
                ))
                .count();

        System.out.println(projectRoles.iterator().next());

        System.out.print("PROJECTS COUNT: ");
        System.out.println(sectorCount + numberOfRolesAndTools);
        System.out.println(sectorCount);
        System.out.println(numberOfRolesAndTools);
        return sectorCount + numberOfRolesAndTools;
    }

    /**
     * @param projectRoles
     * @param expertProjectRoles
     * @return
     */
    private List<Long> countCommonalities(Set<ProjectRole> projectRoles, Set<ProjectRole> expertProjectRoles) {
        List<Long> totalMatchCount = new ArrayList<>();

        ProjectRole projectRole = projectRoles.iterator().next();

        Sector sector = projectRole.getSector();
        Set<Role> roles = projectRole.getRoles();
        Set<Tool> tools = projectRole.getTools();

        for (ProjectRole expertProjectRole : expertProjectRoles) {
            long count = 0;

            Sector expertSector = expertProjectRole.getSector();
            Set<Role> expertRoles = expertProjectRole.getRoles();
            Set<Tool> expertTools = expertProjectRole.getTools();

            // If precise matching is turned on, the sectors have to be equal for a match.
            if (PRECISE_MATCHING && !sector.getSectorName().equals(expertSector.getSectorName())) {
                break;
            }

            if (sector.getSectorName().equals(expertSector.getSectorName())) {
                count++;
            }
            count += roles.stream()
                    .map(Role::getRoleName)
                    .filter(roleName -> expertRoles.stream().anyMatch(expertRole -> roleName.equals(expertRole.getRoleName())))
                    .count();
            count += tools.stream()
                    .map(Tool::getToolName)
                    .filter(toolName -> expertTools.stream().anyMatch(expertTool -> toolName.equals(expertTool.getToolName())))
                    .count();

            totalMatchCount.add(count);
        }


        return totalMatchCount;
    }

    /**
     * @param matchResults
     * @return
     */
    private List<MatchResult> getTopExpertProfileMatches(List<MatchResult> matchResults) {
        quickSort(matchResults, Comparator.comparing(MatchResult::getMatchingScore).reversed(), 0, matchResults.size() - 1);

        return matchResults.subList(0, Math.min(10, matchResults.size()));
    }

    /**
     * Recursive quick sort for sorting a list of items.
     *
     * @param items
     * @param comparator
     * @param low
     * @param high
     */
    private void quickSort(List<MatchResult> items, Comparator<MatchResult> comparator, int low, int high) {
        if (low > high) return;

        int pivotIndex = partition(items, comparator, low, high);

        // Recursively sort elements before and after the pivot
        quickSort(items, comparator, low, pivotIndex - 1);
        quickSort(items, comparator, pivotIndex + 1, high);
    }

    /**
     * @param items
     * @param comparator
     * @param low
     * @param high
     * @return
     */
    private int partition(List<MatchResult> items, Comparator<MatchResult> comparator, int low, int high) {
        int randomIndex = new Random().nextInt(high - low + 1) + low;
        swap(items, randomIndex, high);

        MatchResult pivot = items.get(high);
        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {

            if (comparator.compare(items.get(j), pivot) < 0) {

                i++;
                swap(items, i, j);
            }
        }
        swap(items, i + 1, high);

        return i + 1;
    }

    /**
     * Helper function which swaps two elements in a list
     *
     * @param items
     * @param i
     * @param j
     */
    private void swap(List<MatchResult> items, int i, int j) {
        MatchResult temp = items.get(i);
        items.set(i, items.get(j));
        items.set(j, temp);
    }


    public static void setMinMatchingScore(double minMatchingScore) {
        MIN_MATCHING_SCORE = minMatchingScore;
    }

    public static double getMinMatchingScore() {
        return MIN_MATCHING_SCORE;
    }

    public static boolean isPreciseMatching() {
        return PRECISE_MATCHING;
    }

    public static void setPreciseMatching(boolean preciseMatching) {
        PRECISE_MATCHING = preciseMatching;
    }
}

