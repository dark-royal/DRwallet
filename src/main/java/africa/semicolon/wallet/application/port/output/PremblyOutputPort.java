package africa.semicolon.wallet.application.port.output;

import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.IdentityVerificationRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.IdentityVerificationResponse;

public interface PremblyOutputPort {
    IdentityVerificationResponse verifyIdentityWithPhoneNumber(IdentityVerificationRequest requestDto);
}
