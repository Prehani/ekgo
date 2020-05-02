package ekgo;

public interface  Command <T> {
    void execute(T data);
}
