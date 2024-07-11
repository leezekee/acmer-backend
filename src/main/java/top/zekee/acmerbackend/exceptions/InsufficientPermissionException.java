package top.zekee.acmerbackend.exceptions;

public class InsufficientPermissionException extends RuntimeException {
    String message;

    public InsufficientPermissionException() {
        this.message = "权限不足";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
