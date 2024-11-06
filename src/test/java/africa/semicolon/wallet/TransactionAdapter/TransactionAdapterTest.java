package africa.semicolon.wallet.TransactionAdapter;

import africa.semicolon.wallet.domain.service.TransactionService;
import africa.semicolon.wallet.domain.service.WalletService;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.models.Transaction;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.TransactionEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static africa.semicolon.wallet.domain.models.Status.PENDING;
import static africa.semicolon.wallet.domain.models.TransactionType.DEPOSIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
//@ExtendWith(UserServiceParameterResolver.class)

public class TransactionAdapterTest {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private WalletService walletService;


    @Test
    public void testThatTransactionCanBeCreated() {

        Transaction transaction = Transaction.builder()
                .createdAt(LocalDateTime.now())
                .walletId(302L)
                .userId(2L)
                .status(PENDING)
                .description("MY TRANSACTION")
                .amount(BigDecimal.valueOf(1200))
                .transactionType(DEPOSIT)
                .build();
        transaction = transactionService.createTransaction(transaction);
        assertThat(transaction).isNotNull();
        assertThat(transaction.getCreatedAt()).isNotNull();

    }
    @Sql("/db/data.sql")
    @Test
    public void testThatAllTransactionsCanBeGotten() throws UserNotFoundException {
        Transaction transaction = Transaction.builder()
                .createdAt(LocalDateTime.now())
                .walletId(302L) // Assuming you have a wallet ID setup
                .userId(501L)
                .userEmail("cheist@gmail.com")
                .status(PENDING)
                .description("MY TRANSACTION")
                .amount(BigDecimal.valueOf(1200))
                .transactionType(DEPOSIT)
                .build();

        transaction = transactionService.createTransaction(transaction);
        assertThat(transaction).isNotNull();
        assertThat(transaction.getCreatedAt()).isNotNull();
        List<Transaction> allTransactions = transactionService.getAllTransactionByUserId(501L);
        assertEquals(1, allTransactions.size());
    }

    @Sql("/db/data.sql")
    @Test
    public void testThatInvalidUserCannotGetAllTransactions() {
        Transaction transaction = Transaction.builder()
                .createdAt(LocalDateTime.now())
                .walletId(302L) // Assuming you have a wallet ID setup
                .userId(801L)
                .status(PENDING)
                .description("MY TRANSACTION")
                .amount(BigDecimal.valueOf(1200))
                .transactionType(DEPOSIT)
                .build();

        transaction = transactionService.createTransaction(transaction);
        assertThat(transaction).isNotNull();
        assertThat(transaction.getCreatedAt()).isNotNull();
        assertThrows(UserNotFoundException.class,()-> transactionService.getAllTransactionByUserId(801L));

    }

}
