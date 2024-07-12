package top.zekee.acmerbackend.exceptions;

public class EnumTypeException extends RuntimeException {
    String message;

    public EnumTypeException(String message){
        this.message = message;
    }

    public EnumTypeException(){
        this.message = "枚举类型错误";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
