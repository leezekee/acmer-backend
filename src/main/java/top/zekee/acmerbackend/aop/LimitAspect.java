package top.zekee.acmerbackend.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import top.zekee.acmerbackend.anno.Limit;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.zekee.acmerbackend.exceptions.FrequentRequestException;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class LimitAspect {
    private static ConcurrentHashMap<String, ExpiringMap<String, Integer>> book = new ConcurrentHashMap<>();

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 层切点
     */
    @Pointcut("@annotation(limit)")
    public void controllerAspect(Limit limit) {
    }

    @Around("controllerAspect(limit)")
    public Object doAround(ProceedingJoinPoint pjp, Limit limit) throws Throwable {
        // 获得request对象
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        // 获取Map value对象， 如果没有则返回默认值
        // getOrDefault获取参数，获取不到则给默认值
        ExpiringMap<String, Integer> uc = book.getOrDefault(request.getRequestURI(), ExpiringMap.builder().variableExpiration().build());
        Integer uCount = uc.getOrDefault(request.getRemoteAddr(), 0);
        if (uCount >= limit.value()) { // 超过次数，不执行目标方法
            log.error("接口拦截：{} 请求超过限制频率【{}次/{}ms】,IP为{}", request.getRequestURI(), limit.value(), limit.interval(), request.getRemoteAddr());
            throw new FrequentRequestException();
        } else if (uCount == 0) { // 第一次请求时，设置有效时间
            uc.put(request.getRemoteAddr(), uCount + 1, ExpirationPolicy.CREATED, limit.interval(), TimeUnit.MILLISECONDS);
        } else { // 未超过次数， 记录加一
            uc.put(request.getRemoteAddr(), uCount + 1);
        }
        book.put(request.getRequestURI(), uc);

//        Long currentTime = new Date().getTime();
//        System.out.println(currentTime);
//        if(redisTemplate.hasKey("limit")) {
//            Integer count = redisTemplate.opsForZSet().rangeByScore("limit", currentTime -  intervalTime, currentTime).size();        // intervalTime是限流的时间
//            System.out.println(count);
//            if (count != null && count > 5) {
//                throw new FrequentRequestException();
//            }
//        }
//        redisTemplate.opsForZSet().add("limit", UUID.randomUUID().toString(),currentTime);
        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();

        return result;
    }

}
