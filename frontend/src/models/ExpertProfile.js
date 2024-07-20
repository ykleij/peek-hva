export class ExpertProfile {
    constructor(id, user) {
        this.id = id;
        this.user = user;
    }

    static create(pId = 0, pUser) {
        return new ExpertProfile(
            pId, pUser
        );
    }


    static copyConstructor(expertProfile) {
        if (expertProfile == null) {
            return null;
        }
        return Object.assign(new ExpertProfile(), expertProfile);
    }
}