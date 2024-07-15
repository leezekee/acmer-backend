package top.zekee.acmerbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zekee.acmerbackend.pojo.PageBean;
import top.zekee.acmerbackend.pojo.Response;
import top.zekee.acmerbackend.service.UserRankingSerivice;
import top.zekee.acmerbackend.vo.UserRankVo;

import java.util.List;

@RestController
@RequestMapping("/cfRanking")
@Tag(name = "用户排名接口")
public class UserRankingController {
    UserRankingSerivice userRankingSerivice;

    @Autowired
    public UserRankingController(UserRankingSerivice userRankingSerivice) {
        this.userRankingSerivice = userRankingSerivice;
    }

    @GetMapping("/list")
    @Operation(summary = "获取用户排名")
    public Response getUserRankingList(Integer pageNum, Integer pageSize) {
        PageBean<UserRankVo> userRankVos = userRankingSerivice.getUserRankingList(pageNum, pageSize);
        return Response.success("获取成功", userRankVos);
    }
}
