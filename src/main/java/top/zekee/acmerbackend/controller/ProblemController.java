package top.zekee.acmerbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zekee.acmerbackend.dto.ProblemFilterDto;
import top.zekee.acmerbackend.pojo.Problem;
import top.zekee.acmerbackend.pojo.Response;
import top.zekee.acmerbackend.service.ProblemService;
import top.zekee.acmerbackend.pojo.PageBean;
import top.zekee.acmerbackend.vo.ProblemVo;

@RestController
@RequestMapping("/problem")
@Tag(name = "问题接口", description = "题目相关接口")
public class ProblemController {
    ProblemService problemService;

    @Autowired
    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @GetMapping("/list")
    @Operation(summary = "获取题目列表")
    public Response getProblemList(@Valid ProblemFilterDto problemFilterDto) {
        PageBean<ProblemVo> problems = problemService.findProblems(problemFilterDto);
        return Response.success("获取题目成功", problems);
    }
}
