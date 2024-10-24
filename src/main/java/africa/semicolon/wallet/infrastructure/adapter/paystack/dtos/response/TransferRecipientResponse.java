package africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TransferRecipientResponse {
    public String status;
    public String message;
    public Data data;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Data {
        private String recipientCode;
        private String type;
        private String name;
        private String accountNumber;
        private String bankCode;
        private String currency;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
