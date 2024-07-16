package top.zekee.acmerbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.zekee.acmerbackend.pojo.*;
import top.zekee.acmerbackend.vo.AdminCFUserVo;
import top.zekee.acmerbackend.vo.AdminUserVo;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AdminMapper {

    void updateProblems(List<Problem> problems);

    void updateProblemStatistics(List<ProblemStatistic> problemStatistics);

    void updateContests(List<Contest> contests);

    void updateCFUsers(List<CFUser> cfUsers);

    void updateUser(User u);

    List<AdminCFUserVo> findAllCFUser();

    void deleteCFUser(String handle);

    List<AdminUserVo> findAllUser();

    void updateCFRanking(List<CFUserRanking> results);

    void updateCFSubmissions(List<CFSubmission> submissions);
}
