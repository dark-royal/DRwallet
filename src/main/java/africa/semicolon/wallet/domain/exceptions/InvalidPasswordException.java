package africa.semicolon.wallet.domain.exceptions;

public class InvalidPasswordException extends WalletException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
