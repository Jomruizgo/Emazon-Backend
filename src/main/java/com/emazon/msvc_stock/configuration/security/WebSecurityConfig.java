package com.emazon.msvc_stock.configuration.security;

import com.emazon.msvc_stock.configuration.security.jwt.JwtValidatorFilter;
import com.emazon.msvc_stock.domain.spi.ITokenServicePort;
import com.emazon.msvc_stock.domain.util.Constants;
import com.emazon.msvc_stock.domain.util.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final ITokenServicePort tokenPort;


    public WebSecurityConfig(ITokenServicePort tokenPort) {
        this.tokenPort = tokenPort;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    http.requestMatchers(HttpMethod.POST, Constants.API_ARTICLE_PATH + Constants.ARTICLE_SUPPY_SEMIPATH).hasRole(UserRole.WAREHOUSE.getRoleName());
                    http.requestMatchers(HttpMethod.POST, Constants.API_CATEGORY_PATH).hasRole(UserRole.ADMIN.getRoleName());
                    http.requestMatchers(HttpMethod.POST, Constants.API_BRAND_PATH).hasRole(UserRole.ADMIN.getRoleName());
                    http.requestMatchers(HttpMethod.POST, Constants.API_ARTICLE_PATH).hasRole(UserRole.ADMIN.getRoleName());
                    http.requestMatchers(HttpMethod.GET, Constants.API_CATEGORY_PATH).permitAll();
                    http.requestMatchers(HttpMethod.GET, Constants.API_BRAND_PATH).permitAll();
                    http.requestMatchers(HttpMethod.GET, Constants.API_ARTICLE_PATH).permitAll();
                    http.anyRequest().denyAll();
                })
                .addFilterBefore(new JwtValidatorFilter(tokenPort), UsernamePasswordAuthenticationFilter.class)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);


        return httpSecurity.build();
    }

}