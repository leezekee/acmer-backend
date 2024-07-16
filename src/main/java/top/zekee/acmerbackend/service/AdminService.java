package top.zekee.acmerbackend.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zekee.acmerbackend.dto.AdminUserDto;
import top.zekee.acmerbackend.mapper.AdminMapper;
import top.zekee.acmerbackend.mapper.ProblemMapper;
import top.zekee.acmerbackend.mapper.UserMapper;
import top.zekee.acmerbackend.pojo.*;
import top.zekee.acmerbackend.utils.SpiderUtil;
import top.zekee.acmerbackend.vo.*;

import java.util.*;

@Service
@Slf4j
public class AdminService {
    AdminMapper adminMapper;
    UserService userService;
    UserMapper userMapper;

    ProblemMapper problemMapper;
    SpiderUtil spider = new SpiderUtil();

    @Autowired
    public AdminService(AdminMapper adminMapper, UserService userService, UserMapper userMapper, ProblemMapper problemMapper) {
        this.adminMapper = adminMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.problemMapper = problemMapper;
    }

    public Integer update() {
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
        CFUserRankingVo cfUserRankingVo = spider.getCFUserRanking(handles);
        CFSubmissionVo cfSubmissionVo = spider.getCFSubmissions(handles);
        if (problemsVo == null) {
            return 0;
        }
        if (contestsVo == null) {
            return 0;
        }
        if (cfUserInfoVo == null) {
            return -1;
        }
        if (cfUserRankingVo == null) {
            return -2;
        }
        if (cfSubmissionVo == null) {
            return -3;
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
                Integer maxRating;
                if (cfUser.getRating() != null) {
                    maxRating = Math.max(cfUser.getRating(), ranks.get(cfUser.getHolder()));
                } else {
                    maxRating = ranks.get(cfUser.getHolder());
                }
                ranks.replace(cfUser.getHolder(), maxRating);
            } else {
                ranks.put(cfUser.getHolder(), cfUser.getRating());
            }
        }

        for (Map.Entry<Integer, Integer> entry : ranks.entrySet()) {
            userService.updateUserRankById(entry.getKey(), entry.getValue());
        }

        adminMapper.updateCFRanking(cfUserRankingVo.getResults());
        List<CFSubmission> submissions = new ArrayList<>();

        for (CFSubmission submission : cfSubmissionVo.getSubmissions()) {
//            log.info(submission.toString());
            Problem problem = problemMapper.findProblemByContestIdAndIndex(submission.getContestId(), submission.getProblemIndex());
            if (problem != null) {
                submission.setProblemId(problem.getId());
                submissions.add(submission);
            }
        }

        adminMapper.updateCFSubmissions(submissions);

        return 1;
    }

    public PageBean<AdminUserVo> getUserList(Integer pageNum, Integer pageSize) {
        PageBean<AdminUserVo> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        List<AdminUserVo> users = adminMapper.findAllUser();
        pageBean.setItems(users);
        pageBean.setTotal(users.size());
        return pageBean;
    }

    public void updateUserInfo(AdminUserDto user) {
        User u = user.toUser();
        adminMapper.updateUser(u);
    }

    public AdminUserInfoVo getUserInfo(Integer id) {
        User user = userMapper.findUserById(id);
//        log.info(user.toString());
        List<CFUser> userList = userService.findCFACCountByHolder(id);
        AdminUserInfoVo userVo = new AdminUserInfoVo();
        userVo.setId(user.getId());
        userVo.setSchool(user.getSchool());
        userVo.setClazz(user.getClazz());
        userVo.setGrade(user.getGrade());
        userVo.setName(user.getName());
        userVo.setAward(user.getAward());
        userVo.setUsername(user.getUsername());
        userVo.setCfRanking(user.getCfRanking());
        AdminUserInfoVo.CFAccount cfAccount = new AdminUserInfoVo.CFAccount();
        for (CFUser cfUser : userList) {
            if (cfUser.getAccountType() == 1) {
                cfAccount.setMainAccount(cfUser);
            } else {
                cfAccount.getSubAccount().add(cfUser);
            }
        }
        userVo.setCfAccount(cfAccount);
        return userVo;
    }

    public PageBean<AdminCFUserVo> findAllCFUser(Integer pageNum, Integer pageSize) {
        PageBean<AdminCFUserVo> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        List<AdminCFUserVo> users = adminMapper.findAllCFUser();
        Page<AdminCFUserVo> page = (Page<AdminCFUserVo>) users;

        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }

    public void deleteCFUser(String handle) {
        adminMapper.deleteCFUser(handle);
    }

    public CFUser findCFUserByHandle(String handle) {
        return userMapper.findCFAccountByHandle(handle);
    }
}
