package top.zekee.acmerbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.zekee.acmerbackend.anno.RequireLogin;
import top.zekee.acmerbackend.auth.UserContext;
import top.zekee.acmerbackend.dto.CFAccountDto;
import top.zekee.acmerbackend.dto.LoginDto;
import top.zekee.acmerbackend.dto.RegisterDto;
import top.zekee.acmerbackend.dto.UserUpdateDto;
import top.zekee.acmerbackend.pojo.*;
import top.zekee.acmerbackend.service.UserService;
import top.zekee.acmerbackend.utils.JwtUtil;
import top.zekee.acmerbackend.utils.Md5Util;
import top.zekee.acmerbackend.vo.UserVo;
import top.zekee.acmerbackend.utils.ThreadLocalUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "用户接口")
public class UserController {
    UserService userService;

    StringRedisTemplate stringRedisTemplate;
    UserContext userContext;

    @Autowired
    public UserController(UserService userService, StringRedisTemplate stringRedisTemplate, UserContext userContext) {
        this.userService = userService;
        this.stringRedisTemplate = stringRedisTemplate;
        this.userContext = userContext;
    }


    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public Response register(@RequestBody @Valid RegisterDto authDto) {
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
    public Response login(@RequestBody @Valid LoginDto authDto) {
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

    @GetMapping("/info")
    @Operation(summary = "获取当前用户信息")
    @RequireLogin
    public Response getUserInfo() {
        User user = userContext.getCurrentUser();
//        log.info(user.toString());
        List<CFUser> userList = userService.findCFACCountByHolder(user.getId());
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setSchool(user.getSchool());
        userVo.setClazz(user.getClazz());
        userVo.setGrade(user.getGrade());
        userVo.setName(user.getName());
        userVo.setAward(user.getAward());
        userVo.setUsername(user.getUsername());
        userVo.setCfRanking(user.getCfRanking());
        UserVo.CFAccount cfAccount = new UserVo.CFAccount();
        for (CFUser cfUser : userList) {
            if (cfUser.getAccountType() == 1) {
                cfAccount.setMainAccount(cfUser);
            } else {
                cfAccount.getSubAccount().add(cfUser);
            }
        }
        userVo.setCfAccount(cfAccount);
        return Response.success("获取成功", userVo);
    }

    @GetMapping("/info/{id}")
    @Operation(summary = "获取指定用户信息")
    public Response getUserInfoById(@PathVariable Integer id) {
        User user = userService.findUserById(id);
        return Response.success("获取成功", user);
    }

    @PutMapping("/info")
    @Operation(summary = "修改当前用户信息")
    @RequireLogin
    public Response updateUserInfo(@RequestBody UserUpdateDto userUpdateDto) {
        User user = userContext.getCurrentUser();


        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setSchool(userUpdateDto.getSchool());
        updateUser.setClazz(userUpdateDto.getClazz());
        updateUser.setGrade(userUpdateDto.getGrade());
        updateUser.setName(userUpdateDto.getName());
        updateUser.setPassword(userUpdateDto.getPassword());
        updateUser.setAward(userUpdateDto.getAward());

//        判断部分元素是否为空
        if (updateUser.getSchool() == null
                && updateUser.getClazz() == null
                && updateUser.getGrade() == null
                && updateUser.getName() == null
                && updateUser.getPassword() == null
                && updateUser.getAward() == null) {
            return Response.error(Code.WRONG_PARAMETER, "至少修改一个参数");
        }

        userService.updateUser(updateUser);

        return Response.success("修改成功");
    }

    @PostMapping("/cf/add")
    @Operation(summary = "添加cf账号")
    @RequireLogin
    public Response addCFAccount(@RequestBody @Valid CFAccountDto cfAccountDto) {
        User user = userContext.getCurrentUser();
        Integer id = user.getId();
        String handle = cfAccountDto.getHandle();
        CFUser cfUser = userService.findCFAccountByHandle(handle);
        if (cfUser != null) {
            return Response.error(Code.USERNAME_EXIST, "CF账号已存在");
        }
//        log.info("handle: {}", handle);
        userService.addCFAccount(id, handle);
        return Response.success("添加成功");
    }

    @DeleteMapping("/cf/delete")
    @Operation(summary = "删除cf账号")
    @RequireLogin
    public Response deleteCFAccount(String cfUsername) {
        User user = userContext.getCurrentUser();
        Integer id = user.getId();
        CFUser cfUser = userService.findCFAccountByHandle(cfUsername);
        if (cfUser == null) {
            return Response.error(Code.USERNAME_NOT_EXIST, "CF账号不存在");
        }
        if (!cfUser.getHolder().equals(id)) {
            return Response.error(Code.PERMISSION_DENIED, "无权限删除他人账号");
        }
        userService.deleteCFAccount(cfUsername);
        return Response.success("删除成功");
    }

    @PostMapping("/cf/setMain")
    @Operation(summary = "设置主cf账号")
    @RequireLogin
    public Response setMainCFAccount(@RequestBody @Valid CFAccountDto cfAccountDto) {
        User user = userContext.getCurrentUser();
        Integer id = user.getId();
        String handle = cfAccountDto.getHandle();
        CFUser cfUser = userService.findCFAccountByHandle(handle);
        if (cfUser == null) {
            return Response.error(Code.USERNAME_NOT_EXIST, "CF账号不存在");
        }
        if (!cfUser.getHolder().equals(id)) {
            return Response.error(Code.PERMISSION_DENIED, "无权限设置他人账号");
        }
        userService.setMainCFAccount(id, handle);
        return Response.success("设置成功");
    }

    @GetMapping("/list")
    @Operation(summary = "获取用户列表")
    public Response getUserList(Integer pageNum, Integer pageSize) {
        PageBean<User> userList = userService.findAllUser(pageNum, pageSize);
        return Response.success("获取成功", userList);
    }

    @GetMapping("/rating/contests")
    @Operation(summary = "获取当前用户各比赛后积分变化")
    @RequireLogin
    public Response getCurrentCFUserRanking() {
        User user = userContext.getCurrentUser();
        List<CFUser> cfacCountByHolder = userService.findCFACCountByHolder(user.getId());
        String handle = "";
        for (CFUser cfUser : cfacCountByHolder) {
            if (cfUser.getAccountType() == 1) {
                handle = cfUser.getHandle();
            }
        }
        if (handle.isEmpty()) {
            return Response.error("未绑定CF账户");
        }
        List<CFUserRanking> cfUserRankingList = userService.getCFUserRanking(handle);

        return Response.success("获取成功", cfUserRankingList);
    }

    @GetMapping("/rating/contests/{id}")
    @Operation(summary = "获取指定用户各比赛后积分变化")
    public Response getCFUserRanking(@PathVariable Integer id) {
        List<CFUser> cfacCountByHolder = userService.findCFACCountByHolder(id);
        String handle = "";
        for (CFUser cfUser : cfacCountByHolder) {
            if (cfUser.getAccountType() == 1) {
                handle = cfUser.getHandle();
            }
        }
        if (handle.isEmpty()) {
            return Response.error("未绑定CF账户");
        }
        List<CFUserRanking> cfUserRankingList = userService.getCFUserRanking(handle);

        return Response.success("获取成功", cfUserRankingList);
    }
}
