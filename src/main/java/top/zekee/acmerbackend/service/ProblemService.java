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

import java.util.ArrayList;
import java.util.List;

@Service
public class ProblemService {
    ProblemMapper problemMapper;

    @Autowired
    public ProblemService(ProblemMapper problemMapper) {
        this.problemMapper = problemMapper;
    }

    public PageBean<Problem> findProblems(ProblemFilterDto problemFilterDto) {
        PageBean<Problem> pageBean = new PageBean<>();
        Integer pageNum = problemFilterDto.getPageNum();
        Integer pageSize = problemFilterDto.getPageSize();
        PageHelper.startPage(pageNum, pageSize);

        List<Problem> problems = problemMapper.findProblems(problemFilterDto);
        Page<Problem> page = (Page<Problem>) problems;
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }
}
