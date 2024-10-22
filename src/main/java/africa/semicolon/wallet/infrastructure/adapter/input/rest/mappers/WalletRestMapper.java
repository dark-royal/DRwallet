package africa.semicolon.wallet.infrastructure.adapter.input.rest.mappers;

import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.domain.models.Wallet;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.CreateUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.CreateWalletRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.CreateUserResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.CreateWalletResponse;

public interface WalletRestMapper {

    Wallet toWallet(CreateWalletRequest createWalletRequest);

    CreateWalletResponse tocreateWalletResponse(Wallet wallet);


}
