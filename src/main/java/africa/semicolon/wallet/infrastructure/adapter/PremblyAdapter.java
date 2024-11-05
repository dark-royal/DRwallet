package africa.semicolon.wallet.infrastructure.adapter;

import africa.semicolon.wallet.application.port.output.PremblyOutputPort;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.IdentityVerificationRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.IdentityVerificationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import static africa.semicolon.wallet.domain.models.Constants.*;

@Slf4j
public class PremblyAdapter implements PremblyOutputPort {
    private final RestTemplate restTemplate;
    @Value("${prembly.api.key}")
    private String apikey;
    private final WebClient webClient;

    public PremblyAdapter(RestTemplate restTemplate, WebClient.Builder webClientBuilder) {
        this.restTemplate = restTemplate;
        this.webClient = webClientBuilder.build();
    }

    @Override
    public IdentityVerificationResponse verifyIdentityWithPhoneNumber(IdentityVerificationRequest requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apikey);

        return webClient.post()
                .uri(BVN_VERIFICATION)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(requestDto))
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse -> Mono.error(new Exception("Failed to verify identity: " + clientResponse.statusCode())))
                .bodyToMono(IdentityVerificationResponse.class)
                .doOnError(e -> {
                    log.error("Error occurred during identity verification: {}", e.getMessage());
                })
                .block();

    }
}
