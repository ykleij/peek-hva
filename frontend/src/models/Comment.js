export class Comment {
    constructor(body,
                dateCreated,
                postedOn,
                poster,
    ) {
        this.body = body;
        this.dateCreated = dateCreated;
        this.postedOn = postedOn;
        this.poster = poster;
    }

    static create(pBody, pDateCreated, pPostedOnId, pPosterId) {
        return new Comment(
            pBody,
            pDateCreated,
            pPostedOnId,
            pPosterId
        );
    }


    static copyConstructor(comment) {
        if (comment == null) {
            return null;
        }
        return Object.assign(new Comment(), comment);
    }
}