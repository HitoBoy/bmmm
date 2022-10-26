package bomberman.exceptionHandle;

public class GameEx extends Exception {
    public GameEx() {
    }

    public GameEx(String str) {
        super(str);

    }

    public GameEx(String str, Throwable cause) {
        super(str, cause);

    }

    public GameEx(Throwable cause) {
        super(cause);

    }

}