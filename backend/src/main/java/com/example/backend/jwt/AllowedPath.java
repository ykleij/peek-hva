package com.example.backend.jwt;

import org.springframework.http.HttpMethod;

public class AllowedPath {
    private String path;
    private HttpMethod method;

    public AllowedPath(String path, HttpMethod method) {
        this.path = path;
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }
}
