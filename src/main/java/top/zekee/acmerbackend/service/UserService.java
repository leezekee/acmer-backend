package top.zekee.acmerbackend.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zekee.acmerbackend.mapper.UserMapper;
import top.zekee.acmerbackend.pojo.*;
import top.zekee.acmerbackend.vo.CFSubmissionInfoVo;
import top.zekee.acmerbackend.vo.SubmissionVo;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService {
    UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void addCFAccount(Integer holder, String handle) {
        List<CFUser> cfUsers = userMapper.findCFAccountByHolder(holder);
        if (cfUsers.isEmpty()) {
            userMapper.addCFAccount(holder, handle, 1);
        } else {
            userMapper.addCFAccount(holder, handle, 0);
        }
    }

    public CFUser findCFAccountByHandle(String handle) {
        return userMapper.findCFAccountByHandle(handle);
    }

    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    public int register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userMapper.addUser(user);
        return user.getId();
    }

    public User findUserById(Integer id) {
        return userMapper.findUserById(id);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    public void deleteCFAccount(String cfUsername) {
        userMapper.deleteCFAccount(cfUsername);
    }

    public void setMainCFAccount(Integer id, String cfUsername) {
        List<CFUser> cfUsers = userMapper.findCFAccountByHolder(id);
        for (CFUser cfUser : cfUsers) {
            if (cfUser.getHandle().equals(cfUsername)) {
                cfUser.setAccountType(1);
            } else {
                cfUser.setAccountType(0);
            }
        }
        userMapper.updateAccountType(cfUsers);
    }

    public List<CFUser> findCFACCountByHolder(Integer id) {
        return userMapper.findCFAccountByHolder(id);
    }

    public List<CFUser> findAll() {
        return userMapper.findAll();
    }

    public PageBean<User> findAllUser(Integer pageNum, Integer pageSize) {
        PageBean<User> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.findAllUser();
        Page<User> page = (Page<User>) users;
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }

    public void updateUserRankById(Integer id, Integer ranking) {
        userMapper.updateUserRankById(id, ranking);
    }

    public List<CFUserRanking> getCFUserRanking(String handle) {
        return userMapper.findCfUserRankingByHandle(handle);
    }

    public List<CFSubmission> getWeeklySubmissions() {
        // 获取当前时间戳
        long currentTime = System.currentTimeMillis() / 1000;
        // 获取一周前的时间戳
        long lastWeekTime = currentTime - 7 * 24 * 60 * 60;
        return userMapper.findWeeklySubmissions(lastWeekTime);

    }

    public List<SubmissionVo> countWeeklySubmissions(List<CFSubmission> weeklySubmissions) {
        long currentTime = System.currentTimeMillis() / 1000;
        long timeStartSeconds = currentTime - 6 * 24 * 60 * 60;
//        long dayTwo = currentTime - 6 * 24 * 60 * 60 * 1000;
//        long dayThree = currentTime - 5 * 24 * 60 * 60 * 1000;
//        long dayFour = currentTime - 4 * 24 * 60 * 60 * 1000;
//        long dayFive = currentTime - 3 * 24 * 60 * 60 * 1000;
//        long daySix = currentTime - 2 * 24 * 60 * 60 * 1000;
//        long daySeven = currentTime - 24 * 60 * 60 * 1000;
        List<SubmissionVo> submissionVos = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            submissionVos.add(new SubmissionVo());
        }
        // 根据createTimeSeconds升序排序
        weeklySubmissions.sort((o1, o2) -> (int) (o1.getCreationTimeSeconds() - o2.getCreationTimeSeconds()));
        int index = 0;
        for (int i = 0; i < 7; i++) {
            for (; index < weeklySubmissions.size(); index++) {
                if (weeklySubmissions.get(index).getCreationTimeSeconds() > timeStartSeconds) {
                    break;
                }
                CFSubmission cfSubmission = weeklySubmissions.get(index);
                String verdict = cfSubmission.getVerdict();
                String participantType = cfSubmission.getParticipantType();
                if ("OK".equals(verdict)) {
                    if ("CONTESTANT".equals(participantType)) {
                        submissionVos.get(i).addAcInContest();
                    } else {
                        submissionVos.get(i).addAcInMakeup();
                    }
                } else {
                    if ("CONTESTANT".equals(participantType)) {
                        submissionVos.get(i).addWaInContest();
                    } else {
                        submissionVos.get(i).addWaInMakeup();
                    }
                }
            }
            timeStartSeconds += 24 * 60 * 60;
        }
        return submissionVos;
    }

    public PageBean<CFSubmissionInfoVo> getSubmissions(List<String> handles, Integer pageNum, Integer pageSize) {
        PageBean<CFSubmissionInfoVo> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        List<CFSubmissionInfoVo> cfSubmissions = userMapper.findSubmissionsByHandles(handles);
        Page<CFSubmissionInfoVo> page = (Page<CFSubmissionInfoVo>) cfSubmissions;
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }
}
