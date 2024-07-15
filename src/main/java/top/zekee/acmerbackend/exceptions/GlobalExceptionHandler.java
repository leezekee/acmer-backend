package top.zekee.acmerbackend.exceptions;

import jakarta.validation.ValidationException;
import top.zekee.acmerbackend.pojo.Code;
import top.zekee.acmerbackend.pojo.Response;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(TokenExpiredException.class)
    public Response handleTokenExpiredException(TokenExpiredException exception) {
//        log.error(getStackTraceInfo(exception));
        return Response.error(Code.TOKEN_EXPIRED, exception.getMessage());
    }

    @ExceptionHandler(EnumTypeException.class)
    public Response handleEnumTypeException(EnumTypeException exception) {
//        log.error(getStackTraceInfo(exception));
        return Response.error(Code.WRONG_PARAMETER, exception.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public Response handleValidationException(ValidationException exception) {
        // get cause of the exception
        Throwable cause = exception.getCause();
        if (cause instanceof EnumTypeException) {
            return handleEnumTypeException((EnumTypeException) cause);
        } else {
            return handleException((Exception) cause);
        }
    }

    @ExceptionHandler(InsufficientPermissionException.class)
    public Response handleInsufficientPermissionException(InsufficientPermissionException exception) {
//        log.error(getStackTraceInfo(exception));
        return Response.error(Code.INSUFFICIENT_PERMISSIONS, exception.getMessage());
    }

    @ExceptionHandler(MissingTokenException.class)
    public Response handleMissingTokenException(MissingTokenException exception) {
        return Response.error(Code.INVALID_TOKEN, exception.getMessage());
    }

    @ExceptionHandler(FrequentRequestException.class)
    public Response handleFrequentRequestException(FrequentRequestException exception) {
        return Response.error(Code.FREQUENT_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Response handleConstraintViolationException(ConstraintViolationException exception) {
//        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
//        for (ConstraintViolation<?> violation : violations) {
//            log.error(violation.getMessage());
//        }
        log.error(getStackTraceInfo(exception));
        return Response.error(Code.WRONG_PARAMETER, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        String message = bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage).filter(Objects::nonNull)
                .max(String::compareTo)
                .orElse("参数错误");
//        List<ObjectError> list = bindingResult.getAllErrors();
//        String message = "";
//        for (ObjectError error : list) {
//            message += error.getDefaultMessage() + "; ";
//        }
//        log.error(getStackTraceInfo(exception));
        return Response.error(Code.WRONG_PARAMETER, message);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public Response handleMissingRequestHeaderException(MissingRequestHeaderException exception) {
        return Response.error(Code.WRONG_HEADER, exception.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return Response.error(Code.WRONG_METHOD, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Response handleException(Exception exception) {
//        log.error(Arrays.toString(exception.getStackTrace()));
//        log.error(getStackTraceInfo(exception));
        log.error(exception.toString());
        log.error(exception.getMessage());
//        exception.printStackTrace();
        return Response.error(Code.UNKNOWN_ERROR, "服务器繁忙，请稍后再试");
    }

    public static String getStackTraceInfo(Exception e) {

        StringWriter sw = null;
        PrintWriter pw = null;

        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);//将出错的栈信息输出到printWriter中

            log.error(e.getMessage());
            log.error(e.toString());
            pw.flush();
            sw.flush();

            return sw.toString();
        } catch (Exception ex) {

            return "发生错误";
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
//                    e1.printStackTrace();
                    log.error(e1.getMessage());
                    log.error(e1.toString());
                }
            }
            if (pw != null) {
                pw.close();
            }
        }

    }
}
