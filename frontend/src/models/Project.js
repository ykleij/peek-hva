export class Project {

    constructor(id = 0, name, description, startDate, endDate, status, projectRoles, client) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.projectRoles = projectRoles;
        this.client = client;
    }

    static copyConstructor(project) {
        if (project == null) {
            return null;
        }
        return Object.assign(new Project(), project);
    }
}