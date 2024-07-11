package top.zekee.acmerbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zekee.acmerbackend.anno.Limit;
import top.zekee.acmerbackend.anno.RequireAuth;
import top.zekee.acmerbackend.pojo.Response;
import top.zekee.acmerbackend.service.AdminService;

@RestController
@RequestMapping("/admin")
@Tag(name = "管理员接口")
public class AdminController {
    AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/update")
    @Operation(summary = "更新数据")
    @RequireAuth
    @Limit(interval = 600000, value = 1)
    public Response update() {
        boolean res = adminService.update();
        if (!res) {
            return Response.error("更新失败");
        }
        return Response.success("更新成功");
    }

    @PostMapping("/test")
    @Operation(summary = "测试")
    @RequireAuth
    @Limit(interval = 600000, value = 1)
    public Response test() {
        return Response.success("测试成功");
    }
}
