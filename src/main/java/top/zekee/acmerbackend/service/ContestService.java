package top.zekee.acmerbackend.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zekee.acmerbackend.dto.ContestFilterDto;
import top.zekee.acmerbackend.mapper.ContestMapper;
import top.zekee.acmerbackend.pojo.Contest;
import top.zekee.acmerbackend.pojo.PageBean;

import java.util.List;

@Service
public class ContestService {
    ContestMapper contestMapper;

    @Autowired
    public ContestService(ContestMapper contestMapper) {
        this.contestMapper = contestMapper;
    }

    public PageBean<Contest> findContests(ContestFilterDto contestDto) {
        PageBean<Contest> pageBean = new PageBean<>();
        Integer pageNum = contestDto.getPageNum();
        Integer pageSize = contestDto.getPageSize();
        PageHelper.startPage(pageNum, pageSize);

        List<Contest> contests = contestMapper.findContests(contestDto);
        Page<Contest> page = (Page<Contest>) contests;
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }
}
