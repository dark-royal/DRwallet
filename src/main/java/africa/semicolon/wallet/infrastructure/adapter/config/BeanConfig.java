package africa.semicolon.wallet.infrastructure.adapter.config;

import africa.semicolon.wallet.application.port.output.*;
import africa.semicolon.wallet.domain.service.TransactionService;
import africa.semicolon.wallet.domain.service.UserService;
import africa.semicolon.wallet.domain.service.WalletService;
import africa.semicolon.wallet.infrastructure.adapter.*;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.admin.client.Keycloak;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }

    @Bean
    public UserService userService(UserOutputPort userOutputPort, KeycloakAdapter keycloakAdapter, WalletService walletService, WalletOutputPort walletOutputPort, TransactionService transactionService, UserPersistenceMapper userPersistenceMapper, PasswordEncoder passwordEncoder, PremblyOutputPort premblyOutputPort, IdentityOutputPort identityOutputPort){
        return new UserService(userOutputPort,keycloakAdapter, walletService, walletOutputPort, transactionService,userPersistenceMapper, passwordEncoder,premblyOutputPort,identityOutputPort);
    }



    @Bean
    public WalletService walletService(WalletOutputPort walletOutputPort, PaystackPaymentOutputPort paystackPaymentOutputPort, WalletRepository walletRepository,UserRepository userRepository, UserOutputPort userOutputPort, PayStackAdapter payStackAdapter,UserPersistenceAdapter userPersistenceAdapter){
        return new WalletService(walletOutputPort, paystackPaymentOutputPort, walletRepository, userRepository, userOutputPort, payStackAdapter,userPersistenceAdapter);
    }


    @Bean
    public UserEntity user() {
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
    public TransactionPersistenceAdapter transactionPersistenceAdapter(TransactionRepository transactionRepository, TransactionPersistenceMapper transactionPersistenceMapper) {
        return new TransactionPersistenceAdapter(transactionRepository, transactionPersistenceMapper);
    }

    @Bean
    public TransactionPersistenceMapper transactionPersistenceMapper() {
        return new TransactionPersistenceMapperImpl();
    }

    @Bean
    public TransactionOutputPort transactionOutputPort(TransactionRepository transactionRepository, TransactionPersistenceMapper transactionPersistenceMapper) {
        return new TransactionPersistenceAdapter(transactionRepository, transactionPersistenceMapper);
    }

    @Bean
    public TransactionService transactionService(TransactionOutputPort transactionOutputPort, UserRepository userRepository,TransactionPersistenceMapper transactionPersistenceMapper){
        return new TransactionService(transactionOutputPort,userRepository,transactionPersistenceMapper);
    }


    @Bean
    public UserRestMapper userRestMapper() {
        return new UserRestMapperImpl();
    }

    @Bean
    public TransactionRestMapper transactionRestMapper() {
        return new TransactionRestMapperImpl();
    }

    @Bean
    public WalletRestMapper walletRestMapper() {
        return new WalletRestMapperImpl();
    }



    @Bean
    public KeycloakAdapter keycloakAdapter(UserOutputPort userOutputPort, RestTemplate restTemplate, Keycloak keycloak, ObjectMapper objectMapper){
        return new KeycloakAdapter(userOutputPort,restTemplate,keycloak,objectMapper);
    }


    @Bean
    public PremblyAdapter premblyAdapter(RestTemplate restTemplate, WebClient.Builder webClientBuilder){
        return  new PremblyAdapter(restTemplate,webClientBuilder);
    }

}
      