package africa.semicolon.wallet.domain.exceptions;

public class WalletAlreadyExistAlreadyException extends RuntimeException {
    public WalletAlreadyExistAlreadyException(String message) {
        super(message);
    }
}
