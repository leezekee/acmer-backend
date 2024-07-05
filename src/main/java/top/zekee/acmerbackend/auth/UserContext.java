package top.zekee.acmerbackend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import top.zekee.acmerbackend.exceptions.TokenExpiredException;
import top.zekee.acmerbackend.pojo.User;
import top.zekee.acmerbackend.service.UserService;
import top.zekee.acmerbackend.utils.JwtUtil;
import top.zekee.acmerbackend.utils.ThreadLocalUtil;

import java.util.Map;

@Component
public class UserContext {
    StringRedisTemplate stringRedisTemplate;
    UserService userService;

    @Autowired
    public UserContext(StringRedisTemplate stringRedisTemplate, UserService userService) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.userService = userService;
    }

    public User getCurrentUser() {

        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        return userService.getUserById(id);
    }
}
