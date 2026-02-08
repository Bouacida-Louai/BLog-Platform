package org.sid.blogapp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.blogapp.services.AuthenticationService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = extractToken(request);
            if (token != null) {
                UserDetails userDetails= authenticationService.validateToken(token);
                UsernamePasswordAuthenticationToken authentication=
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);

                if (userDetails  instanceof BlogUserDetails ){
                    request.setAttribute("userId", ((BlogUserDetails) userDetails).getUser().getId());
                }
            }
        }catch (Exception e){
            // Log the exception or handle it as needed
            log.warn("received invalid token: {}", e.getMessage());
        }
        filterChain.doFilter(request,response);



    }

    private String extractToken(HttpServletRequest request) {
       String baererToken= request.getHeader("Authorization");
       if (baererToken != null && baererToken.startsWith("Bearer ")) {
              return baererToken.substring(7);
       }
         return null;
    }
}
