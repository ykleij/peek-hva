package com.example.backend.config;

import com.example.backend.jwt.AllowedPath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class APIConfig implements WebMvcConfigurer {

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.passphrase}")
    private String passphrase;

    @Value("${jwt.tokenDurationOfValidity}")
    private int tokenDurationOfValidity;

    public static final List<String> SECURED_PATHS = Arrays.asList(
            "/users",
            "/projects",
            "/project-roles",
            "/matching",
            "/settings",
            "/invitations",
            "/like",
            "/invitations",
            "/ws",
            "/notifications"
    );

    public static final List<AllowedPath> ALLOWED_PATHS = Arrays.asList(
            new AllowedPath("/users", HttpMethod.POST)
    );

    public String getIssuer() {
        return issuer;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public int getTokenDurationOfValidity() {
        return tokenDurationOfValidity;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(
                        "http://localhost:*", "http://*.hva.nl:*", "https://ewa-frontend-app.onrender.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders(HttpHeaders.AUTHORIZATION,
                        HttpHeaders.CONTENT_TYPE)
                .exposedHeaders(HttpHeaders.AUTHORIZATION,
                        HttpHeaders.CONTENT_TYPE)
                .allowCredentials(true);
    }
}
