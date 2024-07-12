package top.zekee.acmerbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zekee.acmerbackend.mapper.AdminMapper;
import top.zekee.acmerbackend.pojo.CFUser;
import top.zekee.acmerbackend.utils.SpiderUtil;
import top.zekee.acmerbackend.vo.CFUserInfoVo;
import top.zekee.acmerbackend.vo.ContestsVo;
import top.zekee.acmerbackend.vo.ProblemsVo;

import java.util.*;

@Service
public class AdminService {
    AdminMapper adminMapper;
    UserService userService;
    SpiderUtil spider = new SpiderUtil();

    @Autowired
    public AdminService(AdminMapper adminMapper, UserService userService) {
        this.adminMapper = adminMapper;
        this.userService = userService;
    }

    public boolean update() {
        ProblemsVo problemsVo = spider.getCFProblems();
        ContestsVo contestsVo = spider.getCFContests();
        List<CFUser> cfUsers = userService.findAll();
        List<String> handles = new ArrayList<>();
        Map<String, List<Integer>> handleMap = new HashMap<>();
        for (CFUser cfUser : cfUsers) {
            handles.add(cfUser.getHandle());
            handleMap.put(cfUser.getHandle(), Arrays.asList(cfUser.getHolder(), cfUser.getAccountType()));
        }
        CFUserInfoVo cfUserInfoVo = spider.getCFUserInfo(handles);
        if (problemsVo == null) {
            return false;
        }
        if (contestsVo == null) {
            return false;
        }
        if (cfUserInfoVo == null) {
            return false;
        }
        adminMapper.updateProblems(problemsVo.getProblems());
        adminMapper.updateProblemStatistics(problemsVo.getProblemStatistics());
        adminMapper.updateContests(contestsVo.getContests());

        List<CFUser> processedUsers = new ArrayList<>();
        for (CFUser cfUser : cfUserInfoVo.getCfUsers()) {
            if (handleMap.containsKey(cfUser.getHandle())) {
                List<Integer> info = handleMap.get(cfUser.getHandle());
                cfUser.setHolder(info.get(0));
                cfUser.setAccountType(info.get(1));
                processedUsers.add(cfUser);
            }
        }

        adminMapper.updateCFUsers(cfUserInfoVo.getCfUsers());

        Map<Integer, Integer> ranks = new HashMap<>();

        for (CFUser cfUser : cfUserInfoVo.getCfUsers()) {
            if (ranks.containsKey(cfUser.getHolder())) {
                Integer maxRating = Math.max(cfUser.getRating(), ranks.get(cfUser.getHolder()));
                ranks.replace(cfUser.getHolder(), maxRating);
            } else {
                ranks.put(cfUser.getHolder(), cfUser.getRating());
            }
        }

        for (Map.Entry<Integer, Integer> entry : ranks.entrySet()) {
            userService.updateUserRankById(entry.getKey(), entry.getValue());
        }

        return true;
    }
}
