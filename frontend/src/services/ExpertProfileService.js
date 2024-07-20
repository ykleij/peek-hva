import {RestAdaptor} from "@/services/RestAdaptor";

export class ExpertProfileService {
    resourcesUrl;
    entities

    constructor(resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
        this.entities = [];
    }

    async getAllExperts() {
        this.entities = await RestAdaptor.fetchJson(this.resourcesUrl, {
            method: "GET"
        });
        return this.entities;
    }

    async getExpertsByUserId(id) {
        this.entities = await RestAdaptor.fetchJson(this.resourcesUrl + `/user/${id}`, {
            method: "GET"
        });
        return await this.entities;
    }

    async saveRoles(entity) {
        // Checks whether a POST or PUT request needs to be done (if the entity has an id, assume it's an update (PUT)).
        const isUpdate = entity.id !== undefined;

        const url = isUpdate
            ? `${this.resourcesUrl}/${entity.id}`
            : this.resourcesUrl;
        const method = 'PUT';

        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            },
            body: entity,
        });

        const savedEntity = await response.json();
        return savedEntity;
    }

}