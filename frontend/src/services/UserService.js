import {RestAdaptor} from "@/services/RestAdaptor";

export class UserService {
    resourcesUrl;
    entities;

    constructor(resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
        this.entities = [];
    }

    async getUsersFromYesterday() {
        this.entities = await RestAdaptor.fetchJson(this.resourcesUrl + "/amountOfUsersYesterday", {
            method: "GET"
        });
        return this.entities;
    }

    async getUserFromUserProfileId(id) {
        this.entities = await RestAdaptor.fetchJson(this.resourcesUrl + `/user-profile/${id}`, {
            method: "GET"
        });
        return this.entities;
    }


}