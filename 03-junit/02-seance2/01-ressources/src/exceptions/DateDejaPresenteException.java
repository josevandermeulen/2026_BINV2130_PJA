package exceptions;

public class DateDejaPresenteException extends RuntimeException {

    private static final long serialVersionUID = 3027926842047880357L;

    public DateDejaPresenteException() {
    }

    public DateDejaPresenteException(String message) {
        super(message);
    }

}
