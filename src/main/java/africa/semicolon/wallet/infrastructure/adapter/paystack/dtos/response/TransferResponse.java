package africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponse {
    private String status;
    private String message;
    private Data data;
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data {
        private int integration;
        private String domain;
        private int amount;
        private String currency;
        private String source;
        private String reason;
        private int recipient;
        private String status;
        private String transfer_code;
        private int id;
        private String createdAt;
        private String updatedAt;
    }
}
