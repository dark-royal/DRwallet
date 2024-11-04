package africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Builder
@Getter
@Setter
public class TokenRequest {
    private String grantType;
    private String clientId;
    private String clientSecret;
    private String email;
    private String password;

}
