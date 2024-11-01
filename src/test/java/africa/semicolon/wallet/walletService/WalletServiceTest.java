package africa.semicolon.wallet.walletService;

import africa.semicolon.wallet.UserServiceParameterResolver;
import africa.semicolon.wallet.application.port.output.WalletOutputPort;
import africa.semicolon.wallet.application.service.WalletService;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.wallet.domain.exceptions.WalletNotFoundException;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.domain.models.Wallet;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.UserEntity;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.WalletEntity;
import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(UserServiceParameterResolver.class)
public class WalletServiceTest {
    @Autowired
    private  WalletService walletService;
    @Mock
    private WalletOutputPort walletOutputPort;


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
    @Sql("/db/data.sql")
    @Test
    public void testThatFundsCanBeDepositedToTheWallet() throws Exception {
        Wallet wallet = new Wallet(301L, BigDecimal.ZERO); // Initial balance is 0
        User user = new User();
        user.setId(501L);
        user.setEmail("praise@gmail.com");

        Mockito.when(walletOutputPort.getWalletById(wallet.getId())).thenReturn(Optional.of(wallet));

        walletService.depositToWallet(wallet, BigDecimal.valueOf(1000.0), user.getId());
        Wallet updatedWallet = walletService.findWalletById(wallet.getId());
        assertEquals(BigDecimal.valueOf(1600.0).stripTrailingZeros(), updatedWallet.getBalance().stripTrailingZeros());
    }


    @Sql("/db/data.sql")
    @Test
    public void testThatFundsCannotBeDepositedToAnInvalidWallet() throws Exception {
        Wallet wallet = new Wallet();
        User user = new User();
        user.setId(501L);
        user.setEmail("praise@gmail.com");
        wallet.setId(701L);
        wallet.setBalance(BigDecimal.ZERO);
        //WalletEntity updatedWallet = walletRepository.findById(wallet.getId()).orElseThrow(() -> new Exception("Wallet not found after deposit"));
        assertThrows(WalletNotFoundException.class,()->walletService.depositToWallet(wallet, BigDecimal.valueOf(1000.0),user.getId()));
    }

    @Sql("/db/data.sql")
    @Test
    public void testThatInvalidUserIdCannotDepositToAWallet(){
        Wallet wallet = new Wallet();
        User user = new User();
        user.setId(801L);
        user.setEmail("praise1@gmail.com");
        wallet.setId(301L);
        wallet.setBalance(BigDecimal.ZERO);
        //WalletEntity updatedWallet = walletRepository.findById(wallet.getId()).orElseThrow(() -> new Exception("Wallet not found after deposit"));
        assertThrows(UserNotFoundException.class,()->walletService.depositToWallet(wallet, BigDecimal.valueOf(1000.0),user.getId()));
    }


//    @Sql("/db/data.sql")
//    @Test
//    public void testThatFundsCanBeWithdrawnFromTheWallet() throws Exception {
//        Wallet wallet = new Wallet();
//        User user = new User();
//        user.setId(501L);
//        user.setEmail("praise@gmail.com");
//        wallet.setId(301L);
//        wallet.setBalance(BigDecimal.ZERO);
//        walletService.depositToWallet(wallet, BigDecimal.valueOf(1000.0),user.getId());
//        //WalletEntity updatedWallet = walletRepository.findById(wallet.getId()).orElseThrow(() -> new Exception("Wallet not found after deposit"));
//        assertEquals(BigDecimal.valueOf(1600.00).stripTrailingZeros(), wallet.getBalance().stripTrailingZeros());
//
//        walletService.withdrawFromWallet(wallet,BigDecimal.valueOf(1000),"9028979349","058",user.getId());
//        assertEquals(BigDecimal.valueOf(600), wallet.getBalance());
//    }

}