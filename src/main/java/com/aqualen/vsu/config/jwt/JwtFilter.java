package com.aqualen.vsu.config.jwt;

import com.aqualen.vsu.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static io.jsonwebtoken.lang.Strings.hasText;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static String AUTHORIZATION = "authorization";

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isPublicUri(request)){
            filterChain.doFilter(request, response);
            return;
        }

        String token = getTokenFromRequest(request);

        if (token == null || isPublicHttpMethod(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtProvider.validateToken(token);

        String userLogin = jwtProvider.getLoginFromToken(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userLogin);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }

    private boolean isPublicUri(HttpServletRequest request){
        Set<String> uris = new HashSet<>();
        uris.add("/sign-in");
        uris.add("/registration");

        return uris.contains(request.getServletPath());
    }

    private boolean isPublicHttpMethod(HttpServletRequest request) {
        return request.getMethod().equals(HttpMethod.OPTIONS.name());
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
