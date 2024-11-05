package africa.semicolon.wallet.domain.exceptions;

public class WalletNotFoundException extends WalletException {
    public WalletNotFoundException(String message) {
        super(message);
    }
}
