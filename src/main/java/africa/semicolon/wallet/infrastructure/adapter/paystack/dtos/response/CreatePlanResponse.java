package africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response;

import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePlanResponse {

    @JsonProperty("member_id")
    private UserEntity user;

    @JsonProperty("reference")
    private String reference;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("gateway_response")
    private String gatewayResponse;

    @JsonProperty("paid_at")
    private String paidAt;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("channel")
    private String channel;

    @JsonProperty("currency")
    private String currency;
    private boolean status;

    @JsonProperty("ip_address")
    private String ipAddress;

    @JsonProperty("pricing_plan_type")
    private String pricingPlanType;

    private String message;

    @JsonProperty("created_on")
    private Date createdOn = new Date();
}