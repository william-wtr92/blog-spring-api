package com.springboot.blog.config;

import com.springboot.blog.security.JwtAuthenticationEntryPoint;
import com.springboot.blog.security.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
// Provide the schema used in application to Swagger
@SecurityScheme(
        name = "Bear Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SecurityConfig {
    private UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthenticationFilter authenticationFilter;

    public SecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationEntryPoint authenticationEntryPoint, JwtAuthenticationFilter authenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }

    // It used to encode password on the API
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager is used by Spring Security to manage authentification on the API
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // Define the security rules like CSRF and the HTTP autorization on GET/POST/...
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
                // Definition of access rules for each routes
                .authorizeHttpRequests((authorize) ->
                       /* authorize.anyRequest().authenticated()*/

                        // Authorization of GET request matching to the api/** pattern
                        authorize.requestMatchers(HttpMethod.GET, "api/**").permitAll()
                                .requestMatchers("/api/auth/**").permitAll()
                                // Add permission to Get swagger pages in web http://localhost:8080/swagger-ui/index.html#/
                                .requestMatchers("/swagger-ui/**").permitAll()
                                // Add permission to Get JSON format documentation http://localhost:8080/v3/api-docs
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                // Add permission to reach the orders routes to make payment
                                .requestMatchers("/api/orders/**").permitAll()
                                // All other request excluding the 5 patterns below needs to be authenticated requests
                                .anyRequest().authenticated()

                ).exceptionHandling(
                        // Define JwtAuthenticationEntryPoint like entrypoint of request to manage authentication errors
                        exception ->
                                exception.authenticationEntryPoint(authenticationEntryPoint)
                )
                .sessionManagement(
                        /* Configuration of Spring Security to not create HTTP Session. Because we use JWT Token
                         to authenticate user and not session based on cookies like */
                        session ->
                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        /* Add JwtAuthenticationFilter before the standard filter. This allows my custom filter is apply to each incomming request and
        allows the extraction and validation of JWT Token before requests reach other parts of your application. */
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
