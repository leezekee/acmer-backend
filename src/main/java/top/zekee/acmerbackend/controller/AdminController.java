package top.zekee.acmerbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import top.zekee.acmerbackend.anno.Limit;
import top.zekee.acmerbackend.anno.RequireAuth;
import top.zekee.acmerbackend.dto.AdminUserDto;
import top.zekee.acmerbackend.pojo.CFUser;
import top.zekee.acmerbackend.pojo.PageBean;
import top.zekee.acmerbackend.pojo.Response;
import top.zekee.acmerbackend.pojo.User;
import top.zekee.acmerbackend.service.AdminService;
import top.zekee.acmerbackend.vo.AdminCFUserVo;
import top.zekee.acmerbackend.vo.AdminUserInfoVo;
import top.zekee.acmerbackend.vo.AdminUserVo;

@RestController
@RequestMapping("/admin")
@Tag(name = "管理员接口")
public class AdminController {
    AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // 半小时执行一次
    @PostMapping("/update")
    @Operation(summary = "更新数据")
    @RequireAuth
    @Limit(interval = 600000, value = 1)

    public Response update() {
        Integer res = adminService.update();
        if (res == 0) {
            return Response.error("更新失败");
        }
        if (res == -1) {
            return Response.error("系统内存在不存在的cf账户");
        }
        return Response.success("更新成功");
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    public void updateData() {
        adminService.update();
    }

    @PostMapping("/test")
    @Operation(summary = "测试")
    @RequireAuth
    @Limit(interval = 600000, value = 1)
    public Response test() {
        return Response.success("测试成功");
    }

    @GetMapping("/list")
    @Operation(summary = "管理员获取用户列表")
    @RequireAuth
    public Response getUserList(Integer pageNum, Integer pageSize) {
        PageBean<AdminUserVo> userList = adminService.getUserList(pageNum, pageSize);
        return Response.success("获取成功", userList);
    }

    @GetMapping("/user/info/{id}")
    @Operation(summary = "管理员获取用户信息")
    @RequireAuth
    public Response getUserInfo(@PathVariable Integer id) {
        AdminUserInfoVo user = adminService.getUserInfo(id);
        return Response.success("获取成功", user);
    }

    @PutMapping("/user/info")
    @Operation(summary = "管理员修改用户信息")
    @RequireAuth
    public Response updateUserInfo(@RequestBody AdminUserDto user) {
        if (user.getId() == null) {
            return Response.error("用户id不能为空");
        }
        adminService.updateUserInfo(user);
        return Response.success("修改成功");
    }

    @GetMapping("/cf/list")
    @Operation(summary = "获取所有cf账户")
    @RequireAuth
    public Response getCFUserList(Integer pageNum, Integer pageSize) {
        PageBean<AdminCFUserVo> cfUserList = adminService.findAllCFUser(pageNum, pageSize);
        return Response.success("获取成功", cfUserList);
    }

    @DeleteMapping("/cf/del")
    @Operation(summary = "删除cf账户")
    @RequireAuth
    public Response delCFUser(String handle) {
        if (adminService.findCFUserByHandle(handle) == null) {
            return Response.error("cf账户不存在");
        }
        adminService.deleteCFUser(handle);
        return Response.success("删除成功");
    }
}
