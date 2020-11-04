package org.avengers.capstone.hostelrenting.jwt;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.avengers.capstone.hostelrenting.exception.FirebaseIllegalArgumentException;
import org.avengers.capstone.hostelrenting.service.impl.CustomUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FirebaseFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(FirebaseFilter.class);
    private CustomUserService customUserService;
    private HandlerExceptionResolver resolver;

    @Qualifier("handlerExceptionResolver")
    @Autowired
    public void setResolver(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Autowired
    public void setCustomUserService(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            final String requestTokenHeader = request.getHeader("Authorization");
            String phone = null;
            String idToken = null;
            // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                idToken = requestTokenHeader.substring(7);
                boolean checkRevoked = true;
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken, checkRevoked);
                if (decodedToken != null)
                    phone = decodedToken.getUid();

            } else {
                logger.warn("JWT Token does not begin with Bearer String");
            }
            // Once we get the token validate it.
            if (phone != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = this.customUserService.loadUserByUsername(phone);

                // if token is valid configure Spring Security to manually set
                // authentication
                //TODO: check expired
                if (phone.equals(userDetails.getUsername())) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // After setting the Authentication in the context, we specify
                    // that the current user is authenticated. So it passes the
                    // Spring Security Configurations successfully.
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (IllegalArgumentException e) {
            //TODO: skip for development
            FirebaseIllegalArgumentException ex = new FirebaseIllegalArgumentException("Unable to get JWT Token", e);
            resolver.resolveException(request, response, null, ex);
        } catch (FirebaseAuthException e) {
            //TODO: skip for development
            resolver.resolveException(request, response, null, e);
        }
    }
}
