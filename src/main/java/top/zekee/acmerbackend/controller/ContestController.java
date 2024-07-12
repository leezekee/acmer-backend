package top.zekee.acmerbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zekee.acmerbackend.dto.ContestFilterDto;
import top.zekee.acmerbackend.pojo.Contest;
import top.zekee.acmerbackend.pojo.PageBean;
import top.zekee.acmerbackend.pojo.Response;
import top.zekee.acmerbackend.service.ContestService;

@RestController
@RequestMapping("/contest")
@Tag(name = "比赛接口")
public class ContestController {
    ContestService contestService;

    @Autowired
    public ContestController(ContestService contestService) {
        this.contestService = contestService;
    }

    @GetMapping("/list")
    @Operation(summary = "获取比赛列表")
    public Response findContests(@ParameterObject ContestFilterDto contestDto) {
        PageBean<Contest> contestList = contestService.findContests(contestDto);
        return Response.success("获取比赛列表成功", contestList);
    }
}
