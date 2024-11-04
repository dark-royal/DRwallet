package africa.semicolon.wallet.infrastructure.adapter.config;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Bag;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@Getter
public class KeyCloakConfig {

    @Value("${app.keycloak.admin.clientId}")
    private String clientId;
    @Value("${app.keycloak.admin.clientSecret}")
    private String clientSecret;
    @Value("${app.keycloak.realm}")
    private String realm;
    @Value("${app.keycloak.serverUrl}")
    private String serverUrl;

    @Bean
    public Keycloak keyCloak() {
        return KeycloakBuilder.builder()
                .clientSecret(clientSecret)
                .clientId(clientId)
                .grantType("client_credentials")
                .realm(realm)
                .serverUrl(serverUrl)
                .build();
    }
}

//@Configuration
//public class KeyCloakConfig {
//

//

