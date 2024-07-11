package top.zekee.acmerbackend.exceptions;

public class FrequentRequestException extends RuntimeException{
    String message;

    public FrequentRequestException(){
        this.message = "请求过于频繁，请稍后再试";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
