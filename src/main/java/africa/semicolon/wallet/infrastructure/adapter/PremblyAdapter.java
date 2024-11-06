package africa.semicolon.wallet.infrastructure.adapter;

import africa.semicolon.wallet.application.port.output.PremblyOutputPort;
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
    private java.lang.String apikey;
    private final WebClient webClient;

    public PremblyAdapter(RestTemplate restTemplate, WebClient.Builder webClientBuilder) {
        this.restTemplate = restTemplate;
        this.webClient = webClientBuilder.build();
    }

    @Override
    public Mono<IdentityVerificationResponse> verifyBvnNumber(String bvnNumber) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apikey);

        return webClient.post()
                .uri(BVN_VERIFICATION)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(bvnNumber))
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(java.lang.String.class)
                                .map(body -> new Exception("Failed to verify identity: " + clientResponse.statusCode() + " - " + body))
                )
                .bodyToMono(IdentityVerificationResponse.class)
                .doOnError(e -> log.error("Error occurred during identity verification", e));
    }

    @Override
    public Mono<IdentityVerificationResponse> verifyPhoneNumber(String phoneNumber) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apikey);

        return webClient.post()
                .uri(PHONE_NUMBER_VERIFICATION)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(phoneNumber))
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(java.lang.String.class)
                                .map(body -> new Exception("Failed to verify identity: " + clientResponse.statusCode() + " - " + body))
                )
                .bodyToMono(IdentityVerificationResponse.class)
                .doOnError(e -> log.error("Error occurred during identity verification", e));
    }

}
