package africa.semicolon.wallet.domain.exceptions;

public class TransactionNotFoundException extends WalletException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
