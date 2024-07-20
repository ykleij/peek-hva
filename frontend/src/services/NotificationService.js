import {RestAdaptor} from "@/services/RestAdaptor";

export class NotificationService {
    resourcesUrl;
    webSocketService;
    notifications = [];

    constructor(resourcesUrl, webSocketService) {
        this.resourcesUrl = resourcesUrl;
        this.webSocketService = webSocketService;
    }

    async subscribeAdmin(topics, callback) {
        for (const topic of topics) {
            await this.webSocketService.subscribe(topic, callback);
        }
    }

    async saveNotificationForUser(topic, json, userId) {
        const url = `${this.resourcesUrl}/saveForUser/${userId}`;

        try {
            await RestAdaptor.fetchJson(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(json),
            });

            this.notifications.push(json);
        } catch (error) {
            console.error('Error saving notification:', error.message);
        }
    }

    async findAllNotificationsByUserId(userId) {
        this.notifications = await RestAdaptor.fetchJson(`${this.resourcesUrl}/${userId}`, {
            method: 'GET',
        });
    }

    async findByNotificationDetails(notification) {
        const foundNotification = await RestAdaptor.fetchJson(`${this.resourcesUrl}/findByNotification`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(notification)
        });

        if (!foundNotification.ok) {
            console.log("Could not find notification");
        } else {
            this.notifications.push(foundNotification);
        }
    }

    async updateNotificationBatchForUser(notifications) {
        try {
            const response = await RestAdaptor.fetchJson(`${this.resourcesUrl}/updateBatch`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(notifications),
            });

            console.log(response)
        } catch (error) {
            console.error('Error saving notifications batch:', error.message);
        }
    }
}