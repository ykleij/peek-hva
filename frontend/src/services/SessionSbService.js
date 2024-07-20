export class SessionSbService {
    BROWSER_STORAGE_ITEM_NAME;
    RESOURCES_URL;

    constructor(resourcesUrl, browserStorageItemName) {
        this.BROWSER_STORAGE_ITEM_NAME = browserStorageItemName;
        this.RESOURCES_URL = resourcesUrl;
        this.getTokenFromBrowserStorage();
    }

    async asyncSignIn(email, password) {
        const body = JSON.stringify({
            email: email,
            password: password,
        });
        let response = await fetch(this.RESOURCES_URL + '/login', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: body,
            credentials: 'include',
        });

        if (response.ok) {
            const user = await response.json();
            this.saveTokenIntoBrowserStorage(
                response.headers.get('Authorization'),
                user
            );

            return user;
        } else {
            return null;
        }
    }

    signOut() {
        window.sessionStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
        window.sessionStorage.removeItem('user');
        window.localStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
        window.localStorage.removeItem('user');
    }

    saveTokenIntoBrowserStorage(token, user) {
        window.sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token);
        window.sessionStorage.setItem('user', JSON.stringify(user));
        window.localStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token);
        window.localStorage.setItem('user', JSON.stringify(user));
    }

    saveUserIntoBrowserStorage(user) {
        window.sessionStorage.setItem('user', JSON.stringify(user));
        window.localStorage.setItem('user', JSON.stringify(user));
    }

    getTokenFromBrowserStorage() {
        let token = window.sessionStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME);

        if (token == null) {
            token = window.localStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME);
        }

        return token;
    }

    getUserFromBrowserStorage() {
        try {
            let user = window.sessionStorage.getItem('user');
            if (user == null) user = window.localStorage.getItem('user');
            if (user !== null) return JSON.parse(user);

            return null;
        } catch (e) {
            return null;
        }
    }

    getUserFirstNameFromBrowserStorage() {
        try {
            let user = this.getUserFromBrowserStorage();
            if (user == null) return null;
            return user.userProfile.firstName;
        } catch (e) {
            return ""
        }
    }

    getUserFirstLastNameFromBrowserStorage() {
        try {
            let user = this.getUserFromBrowserStorage();
            if (user == null) return null;
            return user.userProfile.firstName + " " + user.userProfile.lastName;
        } catch (e) {
            return "";
        }
    }

    getUserIdFromBrowserStorage() {
        let user = this.getUserFromBrowserStorage();
        if (user == null) return null;
        return user.id;
    }

    getUserRoleFromBrowserStorage() {
        let user = this.getUserFromBrowserStorage();
        if (user == null) return null;
        return user.role;
    }

    isAuthenticated() {
        return this.getTokenFromBrowserStorage() !== null;
    }

    isAdmin() {
        try {
            let user = window.sessionStorage.getItem('user');
            if (!user) user = window.localStorage.getItem('user');
            if (!user) return;

            let role = JSON.parse(user).role;

            return this.isAuthenticated() === true && role === 'admin';
        } catch (e) {
            return false;
        }
    }
}
