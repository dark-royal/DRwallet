package africa.semicolon.wallet.domain.exceptions;

public class AuthenticationException extends WalletException {
    public AuthenticationException(String message) {
        super(message);
    }
}
