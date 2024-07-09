package top.zekee.acmerbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.zekee.acmerbackend.dto.AuthDto;
import top.zekee.acmerbackend.pojo.Code;
import top.zekee.acmerbackend.pojo.Response;
import top.zekee.acmerbackend.pojo.User;
import top.zekee.acmerbackend.service.UserService;
import top.zekee.acmerbackend.utils.JwtUtil;
import top.zekee.acmerbackend.utils.Md5Util;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    Response register(@RequestBody @Validated(AuthDto.Register.class) AuthDto authDto) {
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
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("username", username);
        String token = JwtUtil.genToken(claims);
        return Response.success("注册成功", token);
    }

    @PostMapping("/login")
    Response login(@RequestBody @Validated(AuthDto.Login.class) AuthDto authDto) {
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
        String token = JwtUtil.genToken(claims);
        return Response.success("登录成功", token);
    }
}
