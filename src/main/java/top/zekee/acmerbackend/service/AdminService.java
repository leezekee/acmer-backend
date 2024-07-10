package top.zekee.acmerbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zekee.acmerbackend.mapper.AdminMapper;
import top.zekee.acmerbackend.utils.SpiderUtil;
import top.zekee.acmerbackend.vo.ContestsVo;
import top.zekee.acmerbackend.vo.ProblemsVo;

@Service
public class AdminService {
    AdminMapper adminMapper;
    SpiderUtil spider = new SpiderUtil();

    @Autowired
    public AdminService(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    public boolean update() {
        ProblemsVo problemsVo = spider.getCFProblems();
        ContestsVo contestsVo = spider.getCFContests();
        if (problemsVo == null) {
            return false;
        }
        if (contestsVo == null) {
            return false;
        }
        adminMapper.updateProblems(problemsVo.getProblems());
        adminMapper.updateProblemStatistics(problemsVo.getProblemStatistics());
        adminMapper.updateContests(contestsVo.getContests());
        return true;
    }
}
