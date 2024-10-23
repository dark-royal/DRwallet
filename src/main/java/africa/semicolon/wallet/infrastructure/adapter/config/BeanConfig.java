package africa.semicolon.wallet.infrastructure.adapter.config;

import africa.semicolon.wallet.application.port.output.PaystackPaymentOutputPort;
import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.application.port.output.WalletOutputPort;
import africa.semicolon.wallet.application.service.TransactionService;
import africa.semicolon.wallet.application.service.UserService;
import africa.semicolon.wallet.application.service.WalletService;
import africa.semicolon.wallet.infrastructure.adapter.persistence.UserPersistenceAdapter;
import africa.semicolon.wallet.infrastructure.adapter.persistence.WalletPersistenceAdapter;
import africa.semicolon.wallet.infrastructure.adapter.persistence.mappers.UserPersistenceMapper;
import africa.semicolon.wallet.infrastructure.adapter.persistence.mappers.WalletPersistenceMapper;
import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.UserRepository;
import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.WalletRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



        @Bean
    public UserService userService(UserOutputPort userOutputPort, WalletService walletService){
        return new UserService(userOutputPort,walletService);
    }

        @Bean
    public UserPersistenceAdapter userServicePersistenceAdapter(UserRepository userRepository, UserPersistenceMapper userPersistenceMapper){
        return new UserPersistenceAdapter(userRepository,userPersistenceMapper);
    }

        @Bean
    public WalletService walletService(WalletOutputPort walletOutputPort, PaystackPaymentOutputPort paystackPaymentOutputPort, WalletRepository walletRepository){
        return new WalletService(walletOutputPort,paystackPaymentOutputPort,walletRepository);
    }

    @Bean
    public WalletPersistenceAdapter walletPersistenceAdapter(WalletRepository walletRepository, WalletPersistenceMapper walletPersistenceMapper){
        return new WalletPersistenceAdapter(walletRepository,walletPersistenceMapper);
    }



}

//@Component
//public class BeanConfig {

//    @Bean
//    public TransactionService transactionService(TransactionOutputPort transactionOutputPort){
//        return new TransactionService(transactionOutputPort);
//    };
//    @Bean
//    public PayStackPaymentService payStackPaymentService(UserService userService, PayStackPaymentOutputPort payStackPaymentOutputPort){
//        return new PayStackPaymentService( userService,payStackPaymentOutputPort);
//    };
//    @Bean
//    public UserService userService(UserOutputPort userOutputPort){
//        return new UserService(userOutputPort);
//    }
//    @Bean
//    public UserPersistenceAdapter userServicePersistenceAdapter(UserEntityRepo userEntityRepo){
//        return new UserPersistenceAdapter(userEntityRepo);
//    }
//    @Bean
//    public PayStackPaymentPersistenceAdapter payStackPaymentPersistenceAdapter(PayStackPaymentEntityRepo payStackPaymentEntityRepo){
//        return new PayStackPaymentPersistenceAdapter(payStackPaymentEntityRepo);
//    }
//    @Bean
//    public TransactionPersistenceAdapter transactionPersistenceAdapter(TransactionEntityRepo userEntityRepo){
//        return new TransactionPersistenceAdapter(userEntityRepo);
//    }
//    @Bean
//    public WalletPersistenceAdapter walletPersistenceAdapter(WalletEntityRepo walletEntity){
//        return new WalletPersistenceAdapter(walletEntity) {
//        };
//    }
//}


//import africa.semicolon.wallet.infrastructure.adapter.persistence.mappers.UserPersistenceMapper;
//import africa.semicolon.wallet.infrastructure.adapter.persistence.mappers.WalletPersistenceMapper;
//import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.UserRepository;
//import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.WalletRepository;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//public class BeanConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public UserService userService(UserPersistenceAdapter userPersistenceAdapter, WalletService walletService) {
//        return new UserService(userPersistenceAdapter, walletService);
//    }
//
//    @Bean
//    public UserPersistenceAdapter userServicePersistenceAdapter(UserRepository userRepository, UserPersistenceMapper userPersistenceMapper) {
//        return new UserPersistenceAdapter(userRepository, userPersistenceMapper);
//    }
//
//    @Bean
//    public WalletService walletService(WalletPersistenceAdapter walletPersistenceAdapter,
//                                       PaystackPaymentOutputPort paystackPaymentOutputPort,WalletRepository walletRepository) {
//        return new WalletService(walletPersistenceAdapter, paystackPaymentOutputPort,walletRepository);
//    }
//
//    @Bean
//    public WalletPersistenceAdapter walletPersistenceAdapter(WalletRepository walletRepository, WalletPersistenceMapper walletPersistenceMapper) {
//        return new WalletPersistenceAdapter(walletRepository, walletPersistenceMapper);
//    }
//    @Bean
//    public PaystackPaymentOutputPort paystackPaymentOutputPort() {
//
//        return new PayStackAdapter();
//    }