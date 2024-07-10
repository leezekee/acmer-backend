package top.zekee.acmerbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zekee.acmerbackend.pojo.Response;
import top.zekee.acmerbackend.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/update")
    public Response update() {
        boolean res = adminService.update();
        if (!res) {
            return Response.error("更新失败");
        }
        return Response.success("更新成功");
    }
}
