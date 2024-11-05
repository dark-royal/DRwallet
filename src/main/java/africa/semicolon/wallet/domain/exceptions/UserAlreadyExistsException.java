package africa.semicolon.wallet.domain.exceptions;

public class UserAlreadyExistsException extends WalletException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
