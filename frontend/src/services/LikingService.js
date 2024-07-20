// import {RestAdaptor} from "@/services/RestAdaptor";

export class LikingService {
    resourceUrl;

    constructor(resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
        this.entities = [];
    }

    async fetchJson(url, options = null) {
        let response = await fetch(url, options);
        if (response.ok) {
            return await response.json();
        }
        return null;
    }

    async getAllLikesFromUser(userId) {
        return await this.fetchJson(`${this.resourcesUrl}/${userId}`);
    }

    async likeProject(projectId, userId) {
        await this.fetchJson(`${this.resourcesUrl}/user/${userId}/project/${projectId}`,
            {method: "PUT"});
    }

    async unlikeProject(projectId, userId) {
        await this.fetchJson(`${this.resourcesUrl}/user/${userId}/project/${projectId}`,
            {method: "DELETE"});
    }
}
