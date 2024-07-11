package top.zekee.acmerbackend.exceptions;

public class TokenExpiredException extends RuntimeException {
    String message;
    public TokenExpiredException() {
        this.message = "Token无效，请重新登录";
    }

    @Override
    public String getMessage() {
        return message;
    }
}