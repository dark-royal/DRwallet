//package africa.semicolon.wallet.PaystackAdapter;
//
//import africa.semicolon.wallet.infrastructure.adapter.paystack.PayStackAdapter;
//import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.CreatePlanDto;
//import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.CreatePlanResponse;
//import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.UserRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.http.HttpResponse;
//import org.springframework.http.HttpStatus;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.Optional;
//
//import static africa.semicolon.wallet.infrastructure.adapter.paystack.constant.ApiConstants.PAYSTACK_INITIALIZE_PAY;
//import static africa.semicolon.wallet.infrastructure.adapter.paystack.constant.ApiConstants.PAYSTACK_SECRET_KEY;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//public class PayStackAdapterTest {
//    private final UserRepository userRepository;
//
//    public PayStackAdapterTest(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Test
//    public void testCreatePlan_Success() throws Exception {
//
//        HttpClient mockHttpClient = Mockito.mock(HttpClient.class);
//        ObjectMapper mockMapper = Mockito.mock(ObjectMapper.class);
//        when(userRepository.findById(anyLong())).thenReturn(Optional.empty()); // No user for simplicity
//
//        CreatePlanDto createPlanDto = CreatePlanDto.builder()
//                .name("My Plan")
//                .amount(BigDecimal.valueOf(1200000.34))
//                .interval("monthly")
//                .build();
//        CreatePlanResponse expectedResponse = CreatePlanResponse.builder()
//                .status(true)
//                .message("Plan created successfully")
//                .build();
//        HttpResponse mockHttpResponse = createSuccessfulHttpResponse(); // Mock successful response
//
//        // Mock behavior
//        when(mockHttpClient.execute(any(HttpPost.class))).thenReturn(mockHttpResponse);
//        when(mockMapper.readValue(anyString(), eq(CreatePlanResponse.class))).thenReturn(expectedResponse);
//
//
//        PayStackAdapter payStackAdapter = new PayStackAdapter(userRepository, null); // No payment repo needed here
//        CreatePlanResponse actualResponse = payStackAdapter.createPlan(createPlanDto);
//
//
//        assertEquals(expectedResponse, actualResponse);
//            verify(mockHttpClient).execute(argThat(request -> {
//                assertEquals(PAYSTACK_INITIALIZE_PAY, request.getURI().toString());
//                assertEquals("Bearer " + PAYSTACK_SECRET_KEY, request.getFirstHeader("Authorization").getValue());
//                //assertEquals(CREATE_PLAN_DTO.toJson(), request.getEntity().getContentLength());
//                return true;
//        }));
//        verify(mockMapper).readValue(anyString(), eq(CreatePlanResponse.class));
//    }
//
//    private HttpResponse createSuccessfulHttpResponse() throws IOException {
//        HttpResponse mockHttpResponse = Mockito.mock(HttpResponse.class);
//        when(mockHttpResponse.getStatusLine().getStatusCode()).thenReturn(HttpStatus.CREATED.value());
//
//        String responseBody = "{\"status\": \"success\", \"message\": \"Plan created successfully\"}";
//        when(mockHttpResponse.getEntity().getContent()).thenReturn(new ByteArrayInputStream(responseBody.getBytes()));
//
//        return mockHttpResponse;
//    }
//
//
//}
