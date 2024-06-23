package com.data.siata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import com.data.siata.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Autowired
    private CustomLogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req->req
                                .requestMatchers("/auth/login/**","/auth/register/**","/api/user/search", "/auth/refresh_token/**").permitAll()
                                .requestMatchers("/admin_only/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET,   "/api/statistic/id/**", "/api/gallery/**", "/api/event/**", "/api/destination/**", "api/comment/**", "/api/volunteer/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/user/id/**").hasAnyAuthority("USER","ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/volunteer/**").hasAnyAuthority("USER","ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/user/id/**").hasAnyAuthority("USER","ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ADMIN")
                                .requestMatchers("/**").permitAll()
                                .anyRequest()
                                .authenticated()
                ).userDetailsService(userService)
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        e->e.accessDeniedHandler(
                                        (request, response, accessDeniedException)->response.setStatus(403)
                                )
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .logout(l->l
                        .logoutUrl("/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()
                        ))
                .build();

    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
