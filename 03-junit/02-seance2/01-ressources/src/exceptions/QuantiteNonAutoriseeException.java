package exceptions;

public class QuantiteNonAutoriseeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public QuantiteNonAutoriseeException() {
    }

    public QuantiteNonAutoriseeException(String message) {
        super(message);
    }

}
