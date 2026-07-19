package exceptions;

public class ProduitNonPresentException extends RuntimeException {

    public ProduitNonPresentException() {
    }

    public ProduitNonPresentException(String arg0) {
        super(arg0);
    }

    public ProduitNonPresentException(Throwable arg0) {
        super(arg0);
    }

    public ProduitNonPresentException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public ProduitNonPresentException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

}
