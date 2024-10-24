package africa.semicolon.wallet.infrastructure.adapter.paystack.models;


import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.TransferResponse;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static africa.semicolon.wallet.infrastructure.adapter.paystack.constant.ApiConstants.PAYSTACK_SECRET_KEY;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {
    private String source;
    private BigDecimal amount;
    private String recipient;
    private String reason;
    private String currency;
    private String reference;

    public TransferResponse create() throws Exception {
        URL url = new URL("https://api.paystack.co/transfer");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer"  +  PAYSTACK_SECRET_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        Gson gson = new Gson();
        String jsonInputString = gson.toJson(this);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }


        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return gson.fromJson(response.toString(), TransferResponse.class);
        }
    }
}
