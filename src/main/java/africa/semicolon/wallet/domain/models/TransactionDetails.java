package africa.semicolon.wallet.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Setter
@Getter
@AllArgsConstructor
public class TransactionDetails {
    private Long transactionId;
    private BigDecimal amount;
    private LocalDateTime date;
    private TransactionType transactionType;
}
//@Override
//public TransactionResponse viewAllTransactions(Long userId) throws UserNotFoundException {
//    if (!userOutputPort.existById(userId)) throw new UserNotFoundException("User not found");
//
//    List<Transaction> transactions = transactionService.getAllTransactionByUserId(userId);
//
//    List<TransactionDetail> transactionDetails = transactions.stream()
//        .map(tx -> new TransactionDetail(tx.getId(), tx.getAmount(), tx.getType(), tx.getDate()))
//        .collect(Collectors.toList());
//
//    return new TransactionResponse(userId, transactionDetails);
//}