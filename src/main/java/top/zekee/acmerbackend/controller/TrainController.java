package top.zekee.acmerbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zekee.acmerbackend.anno.RequireLogin;
import top.zekee.acmerbackend.auth.UserContext;
import top.zekee.acmerbackend.pojo.Response;
import top.zekee.acmerbackend.pojo.User;
import top.zekee.acmerbackend.service.TrainService;
import top.zekee.acmerbackend.vo.ProblemSetVo;

import java.util.List;

@RestController
@RequestMapping("/train")
@Tag(name = "智能训练接口")
public class TrainController {
    TrainService trainService;
    UserContext userContext;

    @Autowired
    public TrainController(TrainService trainService, UserContext userContext) {
        this.trainService = trainService;
        this.userContext = userContext;
    }

    @GetMapping("/train")
    @RequireLogin
    @Operation(summary = "获取训练题目")
    public Response getTrainList(Integer setNum) {
        int problemNum = 10;
        int flactuation = 100;
        User user = userContext.getCurrentUser();
        Integer cfRanking = user.getCfRanking();
        if (cfRanking == null) {
            return Response.error("请先绑定CF账号");
        }
        int max = cfRanking + flactuation;
        int min = cfRanking - flactuation;
        List<ProblemSetVo> problemSetVos = trainService.getProblemSet(setNum, min, max);
        return Response.success("获取成功", problemSetVos);
    }
}
