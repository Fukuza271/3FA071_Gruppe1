package rest;

import database.PasswordCheck;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.jersey.http.HttpHeaders;

import java.util.Base64;
import java.util.StringTokenizer;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        if (requestContext.getMethod().equals("OPTIONS")) {
            return;
        }

        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
            String credentials = authHeader.substring(AUTHORIZATION_HEADER_PREFIX.length()).trim();
            byte[] decodedCredentials = Base64.getDecoder().decode(credentials);
            String decodedString = new String(decodedCredentials);
            StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
            String username = tokenizer.nextToken();
            String password = tokenizer.nextToken();

            if (isValidUser(username, password)) {
                return;
            }
        }

        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("User cannot access the resource.").build());
    }

    private boolean isValidUser(String username, String password) {
        return PasswordCheck.login(username, password);
    }
}
