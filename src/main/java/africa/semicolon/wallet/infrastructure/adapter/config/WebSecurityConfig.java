package africa.semicolon.wallet.infrastructure.adapter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> {
            web.ignoring().requestMatchers(
                    HttpMethod.POST,
                    "/public/**",
                    "/users"
            );

            web.ignoring().requestMatchers(
                    HttpMethod.GET,
                    "/public/**"

            );

            web.ignoring().requestMatchers(
                    HttpMethod.DELETE,
                    "/public/**",
                    "/users/{id}"

            );

            web.ignoring().requestMatchers(
                    HttpMethod.PUT,
                    "/public/**",
                    "/users/{id}/send-verification-email"

            );
            web.ignoring().requestMatchers(
                    HttpMethod.OPTIONS,
                    "/**"

            )
                    .requestMatchers("/v3/api-docs/**", "/configuration/**","/swagger-ui/**", "/swagger-resources/**", "swagger-ui.html","/webjars/**", "/api-docs/**");
        };
    }

}



// public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/users/register","/users/login").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        return httpSecurity.build();
//    }