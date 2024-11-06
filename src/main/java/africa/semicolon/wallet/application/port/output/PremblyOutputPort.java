package africa.semicolon.wallet.application.port.output;

import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.IdentityVerificationResponse;
import reactor.core.publisher.Mono;

public interface PremblyOutputPort {
    Mono<IdentityVerificationResponse> verifyBvnNumber(String bvnNumber);
    Mono<IdentityVerificationResponse> verifyPhoneNumber(String phoneNumber);
}
