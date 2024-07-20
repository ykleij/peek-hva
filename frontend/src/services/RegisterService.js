import {RestAdaptor} from "@/services/RestAdaptor";

export class RegisterService {
    resourcesUrl;

    constructor(resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
    }

    async register(body) {
        const response = await RestAdaptor.fetchJson(
            this.resourcesUrl + '/register',
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(body)
            }
        );
        return response;
    }

    async saveUserProfile(profile, userId) {
        try {
            const response = await RestAdaptor.fetchJson(`${this.resourcesUrl}/userProfile/${userId}`, {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(profile),
            });

            return response;
        } catch (e) {
            return null;
        }
    }
}
