package africa.semicolon.wallet.application.port.output;

import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.CreatePlanDto;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.InitializePaymentDto;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.CreatePlanResponse;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.InitializePaymentResponse;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.PaymentVerificationResponse;

public interface PaystackPaymentOutputPort {
    CreatePlanResponse createPlan(CreatePlanDto createPlanDto);
    InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto);
    PaymentVerificationResponse verifyPayment(String reference, Long id, String plan) throws Exception;


}
