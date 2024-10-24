package africa.semicolon.wallet.domain.exceptions;

public class IncorrectPaaswordException extends RuntimeException {
    public IncorrectPaaswordException(String message) {
        super(message);
    }
}
