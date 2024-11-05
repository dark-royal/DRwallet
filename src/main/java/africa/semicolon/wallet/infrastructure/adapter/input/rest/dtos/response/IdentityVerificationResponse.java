package africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;
@Setter
@Getter
@RequiredArgsConstructor
public class IdentityVerificationResponse {
    private boolean success;
    private String status;
    private String message;
    private Map<String, Object> additionalInfo;


}
