import {RestAdaptor} from "@/services/RestAdaptor";

export class ProjectService {
    resourcesUrl;
    entities;

    constructor(resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
        this.entities = [];
    }

    async getProjectsFromYesterday() {
        this.entities = await RestAdaptor.fetchJson(this.resourcesUrl + "/amountOfProjectsYesterday", {
            method: "GET"
        });
        return this.entities;
    }
}