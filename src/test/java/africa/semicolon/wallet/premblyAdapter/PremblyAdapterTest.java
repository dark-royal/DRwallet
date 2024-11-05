package africa.semicolon.wallet.premblyAdapter;

import africa.semicolon.wallet.infrastructure.adapter.PremblyAdapter;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.IdentityVerificationRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.IdentityVerificationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest

public class PremblyAdapterTest {
    @Autowired
    private  PremblyAdapter premblyAdapter;



    @Test
    public void testThatUserBvnCanBeVerified(){
        IdentityVerificationRequest identityVerificationRequest = new IdentityVerificationRequest();
        identityVerificationRequest.setFirstName("Oyewole Funmilayo");
        identityVerificationRequest.setLastName("mary");
        identityVerificationRequest.setPhoneNumber("08136946731");
        identityVerificationRequest.setDateOfBirth("07-05-1979");
        identityVerificationRequest.setBvnNumber("22483542198"); // valid BVN
        identityVerificationRequest.setCountryCode("234");

        IdentityVerificationResponse response = premblyAdapter.verifyIdentityWithPhoneNumber(identityVerificationRequest);
        assertNotNull(response);


    }

    @Test
    public void testThatTheBvnIsInvalid(){
        IdentityVerificationRequest identityVerificationRequest = new IdentityVerificationRequest();
        identityVerificationRequest.setFirstName("Oyewole Funmilayo");
        identityVerificationRequest.setLastName("mary");
        identityVerificationRequest.setPhoneNumber("08136946731");
        identityVerificationRequest.setDateOfBirth("07-05-1979");
        identityVerificationRequest.setBvnNumber("22483542198");
        identityVerificationRequest.setCountryCode("234");
        assertThrows(HttpClientErrorException.class,()-> premblyAdapter.verifyIdentityWithPhoneNumber(identityVerificationRequest));

    }
}
