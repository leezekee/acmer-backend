package top.zekee.acmerbackend.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import top.zekee.acmerbackend.anno.RequireAuth;
import top.zekee.acmerbackend.anno.RequireLogin;
import top.zekee.acmerbackend.exceptions.InsufficientPermissionException;
import top.zekee.acmerbackend.exceptions.MissingTokenException;
import top.zekee.acmerbackend.pojo.User;
import top.zekee.acmerbackend.service.UserService;
import top.zekee.acmerbackend.utils.JsonUtil;
import top.zekee.acmerbackend.utils.JwtUtil;
import top.zekee.acmerbackend.utils.ThreadLocalUtil;
import top.zekee.acmerbackend.exceptions.TokenExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public LoginInterceptor(StringRedisTemplate stringRedisTemplate, UserService userService) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (!method.isAnnotationPresent(RequireLogin.class) && !method.isAnnotationPresent(RequireAuth.class)) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token == null) {
            throw new MissingTokenException();
        }


        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String redisToken = ops.get(token);
        if (redisToken == null) {
//            log.info("missing redis");
            throw new TokenExpiredException();
        }

        Map<String, Object> claims = JwtUtil.parseToken(token);

        if (method.isAnnotationPresent(RequireAuth.class)) {
            Integer auth = (Integer) claims.get("auth");
            if (auth != 0) {
//                log.info("missing auth");
                throw new InsufficientPermissionException();
            }
        }
        ThreadLocalUtil.set(claims);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
