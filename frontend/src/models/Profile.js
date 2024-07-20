export class Profile {
    constructor(id, email, role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    static (pId = 0, pEmail, pRole) {
        return new Profile(pId, pEmail, pRole);
    }

    static copyConstructor(profile) {
        if (profile == null) {
            return null;
        }
        return Object.assign(new Profile(), profile);
    }
}