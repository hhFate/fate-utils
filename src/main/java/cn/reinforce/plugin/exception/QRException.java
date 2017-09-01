package cn.reinforce.plugin.exception;

/**
 * @author 幻幻Fate
 * @create 2017/9/1
 */
public class QRException extends RuntimeException {

    public QRException() {
        super();
    }

    public QRException(String message) {
        super(message);
    }

    public QRException(String message, Throwable cause) {
        super(message, cause);
    }

    public QRException(Throwable cause) {
        super(cause);
    }

    public QRException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
