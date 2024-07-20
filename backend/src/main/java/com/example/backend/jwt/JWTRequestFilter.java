package com.example.backend.jwt;

import com.example.backend.config.APIConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    @Autowired
    APIConfig apiConfig;

//    private final Set<Long> activeUsers = new HashSet<>();  // Store active user IDs

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String fullURL = String.valueOf(request.getRequestURL());
        String servletPath = request.getServletPath();

        // This path is equal to the @RequestMapping annotation in the controllers (i.e. /projects)
        String path = JWTRequestFilter.extractPath(fullURL, servletPath);

        String method = request.getMethod();

        System.out.printf("%nReceived %s request for path: %s%nFull URL: %s%n", method, path, fullURL);

        // Check specific conditions to allow access without a JWT
        // TODO: fix method (isPathAllowedWithoutJwt)
        if (isPathAllowedWithoutJwt(path, method)) {
            chain.doFilter(request, response);
            return;
        }

        // OPTIONS requests and non-secured area should pass through without check
        if (HttpMethod.OPTIONS.matches(method) ||
                APIConfig.SECURED_PATHS.stream().noneMatch(path::startsWith)) {

            chain.doFilter(request, response);
            return;
        }

        // Continue with existing logic for registration invitation URLs
        if (fullURL.contains("/invitations/validation") && HttpMethod.GET.matches(method)) {
            chain.doFilter(request, response);
            return;
        }

        // Websocket requests contain JWT as a query parameter instead of as a header
        String encryptedToken = request.getHeader(HttpHeaders.AUTHORIZATION) != null // If Auth header is null, use query param instead
                ? request.getHeader(HttpHeaders.AUTHORIZATION)
                : request.getParameter("token");

        if (encryptedToken == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No token provided.");
            return;
        }

        try {
            JWToken jwToken = JWToken.decode(encryptedToken.replace("Bearer ", ""),
                    this.apiConfig.getIssuer(), this.apiConfig.getPassphrase());

//            long userId = jwToken.getUserId();
//
//            // TODO: create logic which retrieves the optional userId from the query params to verify the users request belongs to the jwt's userId
//
//            if (path.equals("/ws")) {
//                boolean addedUser = activeUsers.add(userId);
//                if (!addedUser) {
//                    System.out.println("DID NOT ADD USER BECAUSE THEY ALREADY HAVE AN ACTIVE CONNECTION");
//                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "This user already has websocket connection.");
//                    return;
//                }
//                System.out.printf("WS REQUEST: Active users: %s%n", activeUsers);
//            }
//            System.out.printf("NON WS REQUEST: Active users: %s%n", activeUsers);


            // Edge case for when a normal user tries to do anything other than a get request for the projects
            if ((fullURL.contains("/projects/") || fullURL.contains("/project-roles"))
                    && !HttpMethod.GET.matches(request.getMethod())) {
                if (!jwToken.getRole().equals("admin")) {

                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The user is not an admin.");
                    return;
                }
            }

            // pass-on the token info as an attribute for the request
            request.setAttribute(JWToken.JWT_ATTRIBUTE_NAME, jwToken);

            // proceed along the chain of filters towards the REST controller
            chain.doFilter(request, response);

        } catch (RuntimeException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "JWT signature does not match locally computed signature." +
                            " JWT validity cannot be asserted and should not be trusted." +
                            " You need to login first.");
        }
    }

    private boolean isPathAllowedWithoutJwt(String path, String method) {
        return APIConfig.ALLOWED_PATHS.stream()
                .anyMatch(allowedPath ->
                        allowedPath.getPath().equals(path) &&
                                allowedPath.getMethod().name().equals(method)
                );
    }

    public static String extractPath(String fullUrl, String servletPath) {
        try {
            URI uri = new URI(fullUrl);
            String path = uri.getPath();

            // Check if the path starts with the servlet path
            if (path.startsWith(servletPath)) {
                // Extract the first segment after the servlet path
                String[] pathSegments = path.substring(servletPath.length()).split("/");

                // Make sure there are enough segments
                if (pathSegments.length > 1) {
                    return "/" + pathSegments[1];
                } else {
                    throw new IllegalArgumentException("Not enough path segments");
                }
            } else {
                throw new IllegalArgumentException("Servlet path does not match");
            }
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL", e);
        }
    }
}
