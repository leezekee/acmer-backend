package top.zekee.acmerbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.zekee.acmerbackend.dto.LoginDto;
import top.zekee.acmerbackend.dto.RegisterDto;
import top.zekee.acmerbackend.pojo.Code;
import top.zekee.acmerbackend.pojo.Response;
import top.zekee.acmerbackend.pojo.User;
import top.zekee.acmerbackend.service.UserService;
import top.zekee.acmerbackend.utils.JwtUtil;
import top.zekee.acmerbackend.utils.Md5Util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Tag(name = "用户接口")
public class UserController {
    UserService userService;

    StringRedisTemplate stringRedisTemplate;

    @Autowired
    public UserController(UserService userService, StringRedisTemplate stringRedisTemplate) {
        this.userService = userService;
        this.stringRedisTemplate = stringRedisTemplate;
    }


    @PostMapping("/register")
    @Operation(summary = "用户注册")
    Response register(@RequestBody @Valid RegisterDto authDto) {
        String username = authDto.getUsername();
        String password = authDto.getPassword();
        String rePassword = authDto.getRePassword();
        if (!password.equals(rePassword)) {
            return Response.error(Code.WRONG_PASSWORD, "两次密码不一致");
        }

        User userByUsername = userService.findUserByUsername(username);
        if (userByUsername != null) {
            return Response.error(Code.USERNAME_EXIST, "用户名已存在");
        }
        String Md5Password = Md5Util.getMD5String(password);

        int id = userService.register(username, Md5Password);
        Integer userAuth = 1;
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("username", username);
        claims.put("auth", userAuth);
        String token = JwtUtil.genToken(claims);

        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(token, token, 10, TimeUnit.HOURS);

        return Response.success("注册成功", token);
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    Response login(@RequestBody @Valid LoginDto authDto) {
        String username = authDto.getUsername();
        String password = authDto.getPassword();
        User userByUsername = userService.findUserByUsername(username);
        if (userByUsername == null) {
            return Response.error(Code.USERNAME_NOT_EXIST, "用户名不存在");
        }
        String Md5Password = Md5Util.getMD5String(password);
        if (!userByUsername.getPassword().equals(Md5Password)) {
            return Response.error(Code.WRONG_PASSWORD, "密码错误");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userByUsername.getId());
        claims.put("username", username);
        claims.put("auth", userByUsername.getAuth());
        String token = JwtUtil.genToken(claims);

        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(token, token, 10, TimeUnit.HOURS);

        return Response.success("登录成功", token);
    }
}
