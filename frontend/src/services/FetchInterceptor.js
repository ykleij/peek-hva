import fetchIntercept from 'fetch-intercept';

export class FetchInterceptor {
    static theInstance;
    sessionService;
    router;
    unregister;

    constructor(sessionService, router) {
        this.sessionService = sessionService;
        this.router = router;

        FetchInterceptor.theInstance = this;
        this.unregister = fetchIntercept.register(this);
        console.log("FetchInterceptor has been registered; current token = ",
            FetchInterceptor.theInstance.sessionService.getTokenFromBrowserStorage());
    }

    request(url, options) {
        let token = FetchInterceptor.theInstance.sessionService.getTokenFromBrowserStorage();

        if (token == null) {
            return [url, options];
        } else if (options == null) {
            return [url, { headers: { Authorization: token }}];
        } else {
            let newOptions = {...options};
            newOptions.headers = {
                ...options.headers,
                Authorization: token
            }
            return [url, newOptions];
        }
    }

    response(response) {
        FetchInterceptor.theInstance.tryRecoverNewJWToken(response);

        if (response.status >= 400 && response.status < 600) {
            FetchInterceptor.theInstance.handleErrorInResponse(response);
        }
        return response;
    }

    requestError(error) {
        return Promise.reject(error);
    }

    responseError(error) {
        return Promise.reject(error);
    }

    tryRecoverNewJWToken() {
        // const newToken = response.headers.get(FetchInterceptor.theInstance.sessionService.BROWSER_STORAGE_ITEM_NAME)
        //
        // if (newToken) {
        //     FetchInterceptor.theInstance.sessionService.saveTokenIntoBrowserStorage(newToken)
        // }
    }

    handleErrorInResponse(response) {
        if (response.status === 401) {
            FetchInterceptor.theInstance.router.push({ path: "/", query: { logout: true } });
        }
    }




}