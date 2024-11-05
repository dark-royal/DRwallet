package africa.semicolon.wallet.application.port.output;

import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.InitializePaymentDto;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.*;

import java.math.BigDecimal;

public interface PaystackPaymentOutputPort {
    InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto);
    PaymentVerificationResponse verifyPayment(String reference, Long id) throws Exception;
    TransferResponse initiateWithdrawal(BigDecimal amount, String recipientCode, String reason)throws Exception;
    TransferRecipientResponse createRecipient(String name, String accountNumber, String bankCode)throws Exception;
}
