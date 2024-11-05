package africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class IdentityVerificationRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String dateOfBirth;
    private String bvnNumber;
    private String countryCode;
}
