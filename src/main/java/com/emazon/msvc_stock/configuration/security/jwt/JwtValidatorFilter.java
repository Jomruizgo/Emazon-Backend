package com.emazon.msvc_stock.configuration.security.jwt;

import com.emazon.msvc_stock.domain.exceptions.InvalidTokenException;
import com.emazon.msvc_stock.domain.spi.ITokenServicePort;
import com.emazon.msvc_stock.domain.util.AuthMessages;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class JwtValidatorFilter extends OncePerRequestFilter {
    private final ITokenServicePort tokenPort;


    public JwtValidatorFilter(ITokenServicePort tokenPort) {
        this.tokenPort = tokenPort;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwtToken != null) {
            jwtToken = jwtToken.substring(7);

            if (!tokenPort.isValidToken(jwtToken)) {
                throw new InvalidTokenException(AuthMessages.INVALID_TOKEN_MESSAGE);
            }

            String username = tokenPort.extractUsername(jwtToken);
            String stringAuthorities = tokenPort.extractSpecificClaim(jwtToken,"authorities");
            System.out.println(stringAuthorities);
            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            context.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(context);

        }
        filterChain.doFilter(request, response);


    }
}