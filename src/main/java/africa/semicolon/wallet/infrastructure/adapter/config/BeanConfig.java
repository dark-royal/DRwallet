package africa.semicolon.wallet.infrastructure.adapter.config;

import africa.semicolon.wallet.application.port.output.PaystackPaymentOutputPort;
import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.application.port.output.WalletOutputPort;
import africa.semicolon.wallet.application.service.TransactionService;
import africa.semicolon.wallet.application.service.UserService;
import africa.semicolon.wallet.application.service.UserWalletMediator;
import africa.semicolon.wallet.application.service.WalletService;
import africa.semicolon.wallet.infrastructure.adapter.paystack.PayStackAdapter;
import africa.semicolon.wallet.infrastructure.adapter.paystack.repository.PaystackPaymentRepository;
import africa.semicolon.wallet.infrastructure.adapter.persistence.UserPersistenceAdapter;
import africa.semicolon.wallet.infrastructure.adapter.persistence.WalletPersistenceAdapter;
import africa.semicolon.wallet.infrastructure.adapter.persistence.mappers.UserPersistenceMapper;
import africa.semicolon.wallet.infrastructure.adapter.persistence.mappers.UserPersistenceMapperImpl;
import africa.semicolon.wallet.infrastructure.adapter.persistence.mappers.WalletPersistenceMapper;
import africa.semicolon.wallet.infrastructure.adapter.persistence.mappers.WalletPersistenceMapperImpl;
import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.UserRepository;
import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.WalletRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserWalletMediator userWalletMediator() {
        return new UserWalletMediator();
    }

    @Bean
    public UserService userService(UserOutputPort userOutputPort, UserWalletMediator userWalletMediator) {
        return new UserService(userOutputPort, userWalletMediator);
    }

    @Bean
    public WalletService walletService(WalletOutputPort walletOutputPort, PaystackPaymentOutputPort paystackPaymentOutputPort, WalletRepository walletRepository, UserWalletMediator userWalletMediator,PayStackAdapter payStackAdapter) {
        return new WalletService(walletOutputPort, paystackPaymentOutputPort, walletRepository, userWalletMediator,payStackAdapter);
    }

    @Bean
    public UserPersistenceAdapter userPersistenceAdapter(UserRepository userRepository, UserPersistenceMapper userPersistenceMapper) {
        return new UserPersistenceAdapter(userRepository, userPersistenceMapper);
    }

    @Bean
    public PayStackAdapter payStackAdapter(UserRepository userRepository, PaystackPaymentRepository paymentRepository){
        return new PayStackAdapter(userRepository,paymentRepository);
    }

    @Bean

    public WalletPersistenceAdapter walletPersistenceAdapter(WalletRepository walletRepository, WalletPersistenceMapper walletPersistenceMapper) {
        return new WalletPersistenceAdapter(walletRepository, walletPersistenceMapper);
    }

    @Bean
    public PaystackPaymentOutputPort paystackPaymentOutputPort(UserRepository userRepository, PaystackPaymentRepository paymentRepository) {
        return new PayStackAdapter(userRepository, paymentRepository);
    }

    @Bean
    public WalletPersistenceMapper walletPersistenceMapper() {
        return new WalletPersistenceMapperImpl();
    }

    @Bean
    public UserPersistenceMapper userPersistenceMapper() {
        return new UserPersistenceMapperImpl();
    }

    @Bean
    public UserOutputPort userOutputPort(UserRepository userRepository, UserPersistenceMapper userPersistenceMapper) {
        return new UserPersistenceAdapter(userRepository, userPersistenceMapper);

    }
//
//    @Bean
//    public TransactionService transactionService(TransactionOutputPort transactionOutputPort) {
//        return new TransactionService(transactionOutputPort);
//    }

//    @Bean
//    public PayStackPaymentService payStackPaymentService(UserService userService, PayStackPaymentOutputPort payStackPaymentOutputPort) {
//        return new PayStackPaymentService(userService, payStackPaymentOutputPort);
//    }
//
//    @Bean
//    public TransactionPersistenceAdapter transactionPersistenceAdapter(TransactionEntityRepo transactionEntityRepo) {
//        return new TransactionPersistenceAdapter(transactionEntityRepo);
//    }

//    @Bean
//    public WalletPersistenceAdapter walletPersistenceAdapter(WalletRepository walletEntityRepo, WalletPersistenceMapper walletPersistenceMapper) {
//        return new WalletPersistenceAdapter(walletEntityRepo,walletPersistenceMapper);
//    }
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