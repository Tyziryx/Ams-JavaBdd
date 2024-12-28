package Exception;

public class IconException extends RuntimeException {
    public IconException(String message) {
        super(message);
    }

    public IconException(String message, Throwable cause) {
        super(message, cause);
    }
}

