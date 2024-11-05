package africa.semicolon.wallet.domain.exceptions;

public class UserNotFoundException extends WalletException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
