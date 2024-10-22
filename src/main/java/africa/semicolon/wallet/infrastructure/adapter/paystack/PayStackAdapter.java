package africa.semicolon.wallet.infrastructure.adapter.paystack;

import africa.semicolon.wallet.application.port.output.PaystackPaymentOutputPort;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.CreatePlanDto;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.InitializePaymentDto;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.CreatePlanResponse;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.InitializePaymentResponse;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.PaymentVerificationResponse;
import africa.semicolon.wallet.infrastructure.adapter.paystack.models.PaymentPaystack;
import africa.semicolon.wallet.infrastructure.adapter.paystack.models.PricingPlanType;
import africa.semicolon.wallet.infrastructure.adapter.paystack.repository.PaystackPaymentRepository;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.UserEntity;
import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Optional;

import static africa.semicolon.wallet.infrastructure.adapter.paystack.constant.ApiConstants.*;



public class PayStackAdapter implements PaystackPaymentOutputPort {

    private final UserRepository userRepository;
    private final PaystackPaymentRepository paymentRepository;

    @Value("${applyforme.paystack.secret.key}")
    private String paystackSecretKey;

    public PayStackAdapter(UserRepository userRepository, PaystackPaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
    }


    @Override
    public CreatePlanResponse createPlan(CreatePlanDto createPlanDto) {
        CreatePlanResponse createPlanResponse = null;

        try {
            Gson gson = new Gson();
            StringEntity postingString = new StringEntity(gson.toJson(createPlanDto));
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(PAYSTACK_INIT);
            post.setEntity(postingString);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer " + paystackSecretKey);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(post);

            if (response.getStatusLine(). getStatusCode() == STATUS_CODE_CREATED) {

                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw new Exception ("Paystack is unable to process payment at the moment " +
                        "or something wrong with request");
            }

            ObjectMapper mapper = new ObjectMapper();
            createPlanResponse = mapper.readValue(result.toString(), CreatePlanResponse.class);
        } catch(Throwable ex) {
            ex.printStackTrace();
        }
        return createPlanResponse;
    }


    @Override
    public InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto) {
        InitializePaymentResponse initializePaymentResponse = null;

        try {
            Gson gson = new Gson();
            StringEntity postingString  = new StringEntity(gson.toJson(initializePaymentDto));
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(PAYSTACK_INITIALIZE_PAY);
            post.setEntity(postingString);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer " + paystackSecretKey);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(post);

            if (response.getStatusLine(). getStatusCode() == STATUS_CODE_OK) {

                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw new Exception("Paystack is unable to initialize payment at the moment");
            }

            ObjectMapper mapper = new ObjectMapper();
            initializePaymentResponse = mapper. readValue(result.toString(), InitializePaymentResponse.class);
        } catch(Throwable ex) {
            ex.printStackTrace();
        }
        return initializePaymentResponse;
    }

    @Override
    public PaymentVerificationResponse verifyPayment(String reference, Long id, String plan) throws Exception {
        PaymentVerificationResponse paymentVerificationResponse = null;
        PaymentPaystack payment = null;

        try{
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(PAYSTACK_VERIFY + reference);
            request.addHeader("Content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + paystackSecretKey);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(request);

            if (response.getStatusLine(). getStatusCode() == STATUS_CODE_OK) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;

                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw new Exception("Paystack is unable to verify payment at the moment");
            }

            ObjectMapper mapper = new ObjectMapper();
            paymentVerificationResponse = mapper.readValue(result.toString(), PaymentVerificationResponse.class);

            if (paymentVerificationResponse == null || paymentVerificationResponse.getStatus().equals("false")) {
                throw new Exception("An error");
            } else if (paymentVerificationResponse. getData().getStatus().equals("success")) {

                Optional<UserEntity> user = userRepository.findById(id);
                PricingPlanType pricingPlanType = PricingPlanType.valueOf(plan.toUpperCase());

                payment = PaymentPaystack.builder()
                        .user(user.get())
                        .reference(paymentVerificationResponse.getData().getReference())
                        .amount(paymentVerificationResponse.getData().getAmount())
                        .gatewayResponse(paymentVerificationResponse.getData().getGatewayResponse())
                        .paidAt(paymentVerificationResponse.getData().getPaidAt())
                        .createdAt(paymentVerificationResponse.getData().getCreatedAt())
                        .channel(paymentVerificationResponse.getData().getChannel())
                        .currency(paymentVerificationResponse.getData().getCurrency())
                        .ipAddress(paymentVerificationResponse.getData().getIpAddress())
                        .pricingPlanType(pricingPlanType)
                        .createdOn(new Date())
                        .build();
            }
        } catch (Exception ex) {
            throw new Exception("Paystack");
        }
        paymentRepository.save(payment);
        return paymentVerificationResponse;
    }
}
