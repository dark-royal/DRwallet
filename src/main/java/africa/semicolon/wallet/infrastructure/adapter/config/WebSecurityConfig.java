package africa.semicolon.wallet.infrastructure.adapter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
public class WebSecurityConfig {

    @Bean
    public WebSecurityCustomizer customizer(){
            return(web)->{
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
                        "/public/**"

                );

                web.ignoring().requestMatchers(
                        HttpMethod.PUT,
                        "/public/**"

                );

                web.ignoring().requestMatchers(
                        HttpMethod.OPTIONS,
                        "/**"

                )
                        .requestMatchers(
                                "v3/api-docs/**","/configuration/**","/swagger-ui/**","/swagger-resources/**","/swagger-ui.html/**","/webjars/**","/v2/api-docs/**"
                        );




            };
    }

}
