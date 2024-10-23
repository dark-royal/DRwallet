package africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EditProfileRequest {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

}
