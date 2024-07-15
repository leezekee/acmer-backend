package top.zekee.acmerbackend.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.zekee.acmerbackend.utils.JsonUtil;
import top.zekee.acmerbackend.utils.TextColorUtil;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class ExceptionAspect {
    @Pointcut("execution(public * top.zekee.acmerbackend.exceptions.GlobalExceptionHandler.*(..))")
    public void exceptionLog() {
    }

    @Before("exceptionLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
    }

    @AfterReturning(value = "exceptionLog()", returning = "ret")
    public void doAfterReturning(Object ret) throws Throwable {
    }


    @Around("exceptionLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // 请求开始时间
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            HttpServletRequest request = sra.getRequest();
            long startTime = System.currentTimeMillis();

            // MDC
            MDC.put("traceId", UUID.randomUUID().toString());

            // 获取被请求的类和方法
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();


            // 请求入参
            Object[] args = joinPoint.getArgs();
            // 入参转 JSON 字符串
            String argsJsonStr = Arrays.stream(args).map(toJsonStr()).collect(Collectors.joining(", "));

            // 获取请求接口
            String description = request.getRequestURI();

            log.error(TextColorUtil.RED + "====== 请求失败: {}, 消息: {} =================================== "+ TextColorUtil.RESET,
                    description, args);
            return joinPoint.proceed();
        } finally {
            MDC.clear();
        }

    }

    /**
     * 转 JSON 字符串
     * @return
     */
    private Function<Object, String> toJsonStr() {
        return arg -> JsonUtil.toJsonString(arg);
    }

}
