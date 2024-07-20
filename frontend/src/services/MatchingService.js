import {RestAdaptor} from "@/services/RestAdaptor";

export class MatchingService {
    resourcesUrl;

    constructor(resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
    }

    async startAlgorithmAndGetMatchesByProjectId(projectId, adminUserId) {
        const url = `${this.resourcesUrl}/project/${projectId}/users?adminUserId=${adminUserId}`;
        return await RestAdaptor.fetchJson(url);
    }

    async getMatchHistoryByProjectId(projectId) {
        return await RestAdaptor.fetchJson(`${this.resourcesUrl}/project/${projectId}/match-history`);
    }

    async getMatchesFromHistoryByMatchId(matchId) {
        const url = `${this.resourcesUrl}/match-history?matchId=${matchId}`;
        return await RestAdaptor.fetchJson(url);
    }
}