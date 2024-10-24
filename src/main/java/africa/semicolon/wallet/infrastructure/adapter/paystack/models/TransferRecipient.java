package africa.semicolon.wallet.infrastructure.adapter.paystack.models;

import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.TransferRecipientResponse;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static africa.semicolon.wallet.infrastructure.adapter.paystack.constant.ApiConstants.PAYSTACK_SECRET_KEY;

@Setter
@Getter
public class TransferRecipient {
    private String type;
    private String name;
    private String accountNumber;
    private String bankCode;
    private String currency;
    private String description;


    public TransferRecipientResponse create() throws Exception {
        URL url = new URL("https://api.paystack.co/transferrecipient");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer" +  PAYSTACK_SECRET_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);


        Gson gson = new Gson();
        String jsonInputString = gson.toJson(this);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }


        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return gson.fromJson(response.toString(), TransferRecipientResponse.class);
        }
    }
}