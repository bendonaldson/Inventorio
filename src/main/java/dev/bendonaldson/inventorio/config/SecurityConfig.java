package dev.bendonaldson.inventorio.config;

import dev.bendonaldson.inventorio.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // --- PUBLIC ENDPOINTS ---
                        // Allow anyone to register or log in
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // --- ADMIN-ONLY ENDPOINTS ---
                        // Only users with ADMIN can create, update, or delete products and categories
                        .requestMatchers(HttpMethod.POST, "/api/products/**", "/api/categories/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/products/**", "/api/categories/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/products/**", "/api/categories/**").hasAuthority("ADMIN")

                        // --- USER & ADMIN ENDPOINTS ---
                        // Any authenticated user (USER or ADMIN) can view products and categories
                        .requestMatchers(HttpMethod.GET, "/api/products/**", "/api/categories/**").hasAnyAuthority("USER", "ADMIN")

                        // --- ORDER ENDPOINTS ---
                        // Only a regular user can create an order
                        .requestMatchers(HttpMethod.POST, "/api/orders").hasAuthority("USER")
                        // For now, any authenticated user can view their orders. A future step would be to ensure a user can only see their OWN orders.
                        .requestMatchers(HttpMethod.GET, "/api/orders/**").hasAnyAuthority("USER", "ADMIN")

                        // --- FALLBACK RULE ---
                        // Any other request not matched above requires authentication
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}