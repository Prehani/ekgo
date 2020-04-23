package ekgo;

/**
 * Exception thrown when key for get or remove does not exist
 */
public class KeyNotFoundException extends Exception {

    /**
     * no arg constructor
     */
    public KeyNotFoundException() { }

    /**
     * Constructor that provides a message
     * @param msg added message for this exception
     */
    public KeyNotFoundException(String msg) {
        super(msg);
    }

}

