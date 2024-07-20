export class User {
    constructor(email, password) {
        this.email = email;
        this.password = password;
    }

    static () {
        return new User(this.email, this.password);
    }

    static copyConstructor(user) {
        if (user == null) {
            return null;
        }
        return Object.assign(new User(), user);
    }
}