package top.zekee.acmerbackend.exceptions;

public class MissingTokenException extends RuntimeException {
    String message;

    public MissingTokenException() {
        this.message = "请先登录";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
