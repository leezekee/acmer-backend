package top.zekee.acmerbackend.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.zekee.acmerbackend.anno.RequireAuth;
import top.zekee.acmerbackend.anno.RequireLogin;
import top.zekee.acmerbackend.auth.UserContext;
import top.zekee.acmerbackend.pojo.User;
import top.zekee.acmerbackend.utils.JsonUtil;
import top.zekee.acmerbackend.utils.TextColorUtil;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Aspect
@Slf4j
@Component
public class WebLogAspect {

    UserContext userContext;

    @Autowired
    public WebLogAspect(UserContext userContext) {
        this.userContext = userContext;
    }

    @Pointcut("execution(public * top.zekee.acmerbackend.controller.*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
    }

    @AfterReturning(value = "webLog()", returning = "ret")
    public void doAfterReturning(Object ret) throws Throwable {
    }

    @Around("webLog()")
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

            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

            // 请求入参
            Object[] args = joinPoint.getArgs();
            // 入参转 JSON 字符串
            String argsJsonStr = Arrays.stream(args).map(toJsonStr()).collect(Collectors.joining(", "));

            // 获取请求接口
            String description = request.getRequestURI();

            log.info(TextColorUtil.GREEN + "====== 请求开始  地址： {} =================================== " + TextColorUtil.RESET,
                    description);


            if (method.isAnnotationPresent(RequireLogin.class) || method.isAnnotationPresent(RequireAuth.class)) {
                User user = userContext.getCurrentUser();

                log.info(TextColorUtil.GREEN + "====== 请求地址:{}, 请求用户Id: {}, 用户名: {} =================================== "+ TextColorUtil.RESET,
                        description, user.getId(), user.getUsername());
            }

            // 打印请求相关参数
            log.info(TextColorUtil.BLUE + "====== 入参: {}, 请求类: {}, 请求方法: {} ============== "+ TextColorUtil.RESET,
                    argsJsonStr, className, methodName);

            // 执行切点方法
            Object result = joinPoint.proceed();

            // 执行耗时
            long executionTime = System.currentTimeMillis() - startTime;

            // 打印出参等相关信息
            log.info(TextColorUtil.BLUE + "====== 请求结束: {}, 耗时: {}ms, 出参: {} ============= "+ TextColorUtil.RESET,
                    description, executionTime, JsonUtil.toJsonString(result));

            return result;
        }
        finally {
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
