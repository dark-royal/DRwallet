package africa.semicolon.wallet.infrastructure.adapter.config;

import africa.semicolon.wallet.application.port.output.PaystackPaymentOutputPort;
import africa.semicolon.wallet.application.port.output.TransactionOutputPort;
import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.application.port.output.WalletOutputPort;
import africa.semicolon.wallet.application.service.KeycloakUserService;
import africa.semicolon.wallet.application.service.TransactionService;
import africa.semicolon.wallet.application.service.UserService;
import africa.semicolon.wallet.application.service.WalletService;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.mappers.*;
import africa.semicolon.wallet.infrastructure.adapter.paystack.PayStackAdapter;
import africa.semicolon.wallet.infrastructure.adapter.paystack.repository.PaystackPaymentRepository;
import africa.semicolon.wallet.infrastructure.adapter.persistence.TransactionPersistenceAdapter;
import africa.semicolon.wallet.infrastructure.adapter.persistence.UserPersistenceAdapter;
import africa.semicolon.wallet.infrastructure.adapter.persistence.WalletPersistenceAdapter;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.UserEntity;
import africa.semicolon.wallet.infrastructure.adapter.persistence.mappers.*;
import africa.semicolon.wallet.infrastructure.adapter.persistence.mappers.UserRestMapperImpl;
import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.TransactionRepository;
import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.UserRepository;
import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.WalletRepository;
import org.keycloak.admin.client.Keycloak;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public UserService userService(UserOutputPort userOutputPort, WalletService walletService, WalletOutputPort walletOutputPort, TransactionService transactionService, KeycloakUserService keycloakUserService, UserEntity userEntity, UserPersistenceMapper userPersistenceMapper) {
        return new UserService(userOutputPort, walletService, walletOutputPort,transactionService,keycloakUserService,userEntity,userPersistenceMapper);
    }

    @Bean
    public KeycloakUserService keycloakUserService(Keycloak keycloak){
        return new KeycloakUserService(keycloak);
    }

    @Bean
    public WalletService walletService(WalletOutputPort walletOutputPort, PaystackPaymentOutputPort paystackPaymentOutputPort, WalletRepository walletRepository, PayStackAdapter payStackAdapter, UserRepository userRepository,UserOutputPort userOutputPort) {
        return new WalletService(walletOutputPort, paystackPaymentOutputPort, walletRepository, userRepository, userOutputPort,payStackAdapter);
    }

    @Bean
    public UserEntity user(){
        return new UserEntity();
    }

    @Bean
    public UserPersistenceAdapter userPersistenceAdapter(UserRepository userRepository, UserPersistenceMapper userPersistenceMapper) {
        return new UserPersistenceAdapter(userRepository, userPersistenceMapper);
    }

    @Bean
    public PayStackAdapter payStackAdapter(UserRepository userRepository, PaystackPaymentRepository paymentRepository, RestTemplate restTemplate) {
        return new PayStackAdapter(userRepository, paymentRepository, restTemplate);
    }

    @Bean

    public WalletPersistenceAdapter walletPersistenceAdapter(WalletRepository walletRepository, WalletPersistenceMapper walletPersistenceMapper) {
        return new WalletPersistenceAdapter(walletRepository, walletPersistenceMapper);
    }

    @Bean
    public PaystackPaymentOutputPort paystackPaymentOutputPort(UserRepository userRepository, PaystackPaymentRepository paymentRepository, RestTemplate restTemplate) {
        return new PayStackAdapter(userRepository, paymentRepository, restTemplate);
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

    @Bean
    public TransactionPersistenceAdapter transactionPersistenceAdapter(TransactionRepository transactionRepository, TransactionPersistenceMapper transactionPersistenceMapper){
        return new TransactionPersistenceAdapter(transactionRepository, transactionPersistenceMapper);
    }

    @Bean
    public TransactionPersistenceMapper transactionPersistenceMapper(){
        return new TransactionPersistenceMapperImpl();
    }

    @Bean
    public TransactionOutputPort transactionOutputPort(TransactionRepository transactionRepository, TransactionPersistenceMapper transactionPersistenceMapper){
        return new TransactionPersistenceAdapter(transactionRepository, transactionPersistenceMapper);
    }

    @Bean
    public TransactionService transactionService(TransactionOutputPort transactionOutputPort,UserRepository userRepository){
        return new TransactionService(transactionOutputPort,userRepository);
    }

    @Bean
    public UserRestMapper userRestMapper(){
        return new UserRestMapperImpl();
    }

    @Bean
    public TransactionRestMapper transactionRestMapper(){
        return new TransactionRestMapperImpl();
    }

    @Bean
    public WalletRestMapper walletRestMapper(){
        return new WalletRestMapperImpl();
    }

}