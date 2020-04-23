package ekgo;

/**
 * Exception thrown when and insert, remove, or get a null key
 */
public class IllegalNullKeyException extends Exception {

    /**
     * default no-arg constructor
     */
    public IllegalNullKeyException() { }

    /**
     * Constructor that provides a message
     * @param msg added message for this exception
     */
    public IllegalNullKeyException(String msg) {
        super(msg);
    }


}
