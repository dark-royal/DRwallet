package africa.semicolon.wallet.infrastructure.adapter.config;

import africa.semicolon.wallet.application.port.output.PaystackPaymentOutputPort;
import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.application.port.output.WalletOutputPort;
import africa.semicolon.wallet.application.service.UserService;
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
    public UserService userService(UserOutputPort userOutputPort, WalletService walletService, WalletOutputPort walletOutputPort) {
        return new UserService(userOutputPort, walletService, walletOutputPort);
    }

    @Bean
    public WalletService walletService(WalletOutputPort walletOutputPort, PaystackPaymentOutputPort paystackPaymentOutputPort, WalletRepository walletRepository, PayStackAdapter payStackAdapter, UserRepository userRepository,UserOutputPort userOutputPort) {
        return new WalletService(walletOutputPort, paystackPaymentOutputPort, walletRepository, userRepository, userOutputPort,payStackAdapter);
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
}