import {RestAdaptor} from "@/services/RestAdaptor";

export class CommentService {
    resourcesUrl;
    copyConstructor

    constructor(resourcesUrl, copyConstructor) {
        this.resourcesUrl = resourcesUrl;
        this.copyConstructor = copyConstructor;
    }

    async getCommentByPostedOnId(id) {
        this.entities = await RestAdaptor.fetchJson(this.resourcesUrl + `/posted-on/${id}`, {
            method: "GET"
        });
        return await this.entities;
    }

    async saveComment(entity) {
        const savedEntity = await RestAdaptor.fetchJson(this.resourcesUrl, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(entity),
        });
        return savedEntity;
    }

    async deleteComment(id) {
        const response = RestAdaptor.fetchJson(
            this.resourcesUrl + '/' + id,
            {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
            }
        );
        return response;
    }
}