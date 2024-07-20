export class ProjectRole {

    constructor(id = 0, title, description, qualifierValue) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.qualifierValue = qualifierValue;
    }

    static copyConstructor(projectRole) {
        if (projectRole == null) {
            return null;
        }
        return Object.assign(new ProjectRole(), projectRole);
    }
}