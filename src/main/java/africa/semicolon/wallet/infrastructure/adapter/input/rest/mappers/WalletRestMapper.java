package africa.semicolon.wallet.infrastructure.adapter.input.rest.mappers;


import africa.semicolon.wallet.domain.models.Wallet;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.CreateUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.CreateWalletRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.DepositToWalletRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.FindWalletRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.CreateUserResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.CreateWalletResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.DepositToWalletResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.FindWalletResponse;
import org.mapstruct.Mapper;


public interface WalletRestMapper {

    Wallet toWallet(CreateWalletRequest createWalletRequest);

    CreateWalletResponse tocreateWalletResponse(Wallet wallet);

    Wallet toFindWallet(FindWalletRequest findWalletRequest);
    FindWalletResponse tofindWalletResponse(Wallet wallet);

    Wallet toDepositToWallet(DepositToWalletRequest depositToWalletRequest);

    DepositToWalletResponse toDepositToWalletResponse(Wallet wallet);
}
