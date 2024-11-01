package africa.semicolon.wallet.infrastructure.adapter.paystack.models;

import africa.semicolon.wallet.domain.models.CharsetAdapter;
import africa.semicolon.wallet.domain.models.ProxyTypeAdapter;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.TransferRecipientResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.Proxy;
import java.nio.charset.Charset;

@Setter
@Getter
public class TransferRecipient {
    private String type;
    private String name;
    private String accountNumber;
    private String bankCode;
    private String currency;
    private String description;



        private final RestTemplate restTemplate;
        private static final String PAYSTACK_SECRET_KEY = "YOUR_SECRET_KEY"; // Use your actual Paystack secret key
        private static final String PAYSTACK_API_URL = "https://api.paystack.co/transferrecipient";

    public TransferRecipient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public TransferRecipientResponse create() throws Exception {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + PAYSTACK_SECRET_KEY);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Charset.class, new CharsetAdapter())
                .create();


            String jsonInputString = gson.toJson(this);


            HttpEntity<String> requestEntity = new HttpEntity<>(jsonInputString, headers);

        String url = PAYSTACK_API_URL + "/transferrecipient";
        ResponseEntity<TransferRecipientResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, TransferRecipientResponse.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else {
                String errorMessage = "Paystack API returned an error: " + responseEntity.getStatusCode() + " - " + responseEntity.getBody();
                throw new RuntimeException(errorMessage);
            }
        }
    }

