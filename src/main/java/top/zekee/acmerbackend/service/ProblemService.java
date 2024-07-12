package top.zekee.acmerbackend.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import top.zekee.acmerbackend.dto.ProblemFilterDto;
import top.zekee.acmerbackend.mapper.ProblemMapper;
import top.zekee.acmerbackend.pojo.Problem;
import top.zekee.acmerbackend.pojo.PageBean;
import top.zekee.acmerbackend.vo.ProblemVo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProblemService {
    ProblemMapper problemMapper;

    @Autowired
    public ProblemService(ProblemMapper problemMapper) {
        this.problemMapper = problemMapper;
    }

    public PageBean<ProblemVo> findProblems(ProblemFilterDto problemFilterDto) {
        PageBean<ProblemVo> pageBean = new PageBean<>();
        Integer pageNum = problemFilterDto.getPageNum();
        Integer pageSize = problemFilterDto.getPageSize();
        PageHelper.startPage(pageNum, pageSize);

        List<Problem> problems = problemMapper.findProblems(problemFilterDto);
        Page<Problem> page = (Page<Problem>) problems;
        List<ProblemVo> problemVos = new ArrayList<>();
        List<Problem> problemList = page.getResult();
        for (Problem problem : problemList) {
            ProblemVo problemVo = new ProblemVo();
            problemVo.setId(problem.getId());
            problemVo.setContestId(problem.getContestId());
            problemVo.setIndex(problem.getIndex());
            problemVo.setName(problem.getName());
            problemVo.setType(problem.getType());
            problemVo.setPoints(problem.getPoints());
            problemVo.setRating(problem.getRating());
            problemVos.add(problemVo);
            List<String> tags = Arrays.asList(problem.getTags().split(";"));
            problemVo.setTags(tags);
        }

        pageBean.setTotal(page.getTotal());
        pageBean.setItems(problemVos);
        return pageBean;
    }
}
