package africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EditProfileResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

}
