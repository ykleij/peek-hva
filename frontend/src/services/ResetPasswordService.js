export class ResetPasswordService {
    resourcesUrl;


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

    async requestResetPassword(email, baseUrl) {
        const body = JSON.stringify({
            email: email,
            url: baseUrl,
            newPassword: "null"
        });
        let response = await fetch(`${this.resourcesUrl}/request`,
            {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: body
            });

        if (response.ok) {
            return await response.json();
        }
    }

    async resetPassword(url, newPassword) {
        const body = JSON.stringify({
            email: "null",
            url: url,
            newPassword: newPassword
        });
        let response = await fetch(`${this.resourcesUrl}/set`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: body
        });

        if (response.ok) {
            return await response.json();
        }
    }


}
