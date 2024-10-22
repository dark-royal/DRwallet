package africa.semicolon.wallet.domain.exceptions;

public class WalletNotFoundException extends Throwable {
    public WalletNotFoundException(String message) {
        super(message);
    }
}
