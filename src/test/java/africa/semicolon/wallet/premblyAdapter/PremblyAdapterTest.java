package africa.semicolon.wallet.premblyAdapter;

import africa.semicolon.wallet.infrastructure.adapter.PremblyAdapter;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.IdentityVerificationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

public class PremblyAdapterTest {
    @Autowired
    private  PremblyAdapter premblyAdapter;



    @Test
    public void testThatUserBvnCanBeVerified(){
        String bvnNumber = "22483542198";
        Mono<IdentityVerificationResponse> response = premblyAdapter.verifyBvnNumber(bvnNumber);
        assertNotNull(response);


    }

    @Test
    public void testThatTheBvnIsInvalid(){
        String bvnNumber = "2248354219812";
        assertThrows(HttpClientErrorException.class,()-> premblyAdapter.verifyBvnNumber(bvnNumber));

    }

    @Test
    public void testThatUserPhoneNumberCanBeVerified(){
        String phoneNumber = "09028979349";

        Mono<IdentityVerificationResponse> response = premblyAdapter.verifyPhoneNumber(phoneNumber);
        assertNotNull(response);


    }
}
