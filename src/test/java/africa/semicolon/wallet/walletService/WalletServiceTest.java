package africa.semicolon.wallet.walletService;

import africa.semicolon.wallet.UserServiceParameterResolver;
import africa.semicolon.wallet.application.port.output.WalletOutputPort;
import africa.semicolon.wallet.application.service.WalletService;
import africa.semicolon.wallet.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.domain.models.Wallet;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.UserEntity;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.WalletEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(UserServiceParameterResolver.class)
public class WalletServiceTest {
    @Autowired
    private  WalletService walletService;


    @Test
    public void testThatWalletCanBeCreated(){
        Wallet wallet = new Wallet();
        wallet.setId(1L);
        Wallet createdWallet = walletService.createWallet(wallet);
        assertNotNull(createdWallet);
        assertEquals(1L, createdWallet.getId());

    }

    @Test
    public void testThatDuplicateWalletCannotBeCreated(){
        Wallet wallet = new Wallet();
        wallet.setId(1L);
        wallet.setBalance(BigDecimal.ZERO);
        Wallet createdWallet = walletService.createWallet(wallet);
        assertNotNull(createdWallet);
        assertEquals(1L, createdWallet.getId());
        assertThrows(WalletAlreadyExistAlreadyException.class,()->walletService.createWallet(wallet));

    }

    @Test
    @Sql("/db/data.sql")
    public void testThatFundsCanBeDepositedToTheWallet(){
        WalletEntity wallet = new WalletEntity();
        wallet.setId(1L);
        wallet.setUserId(new UserEntity());
        wallet.setBalance(BigDecimal.ZERO);
        walletService.depositToWallet(wallet, BigDecimal.valueOf(1000));
        assertEquals(BigDecimal.valueOf(1000), wallet.getBalance());
    }
}