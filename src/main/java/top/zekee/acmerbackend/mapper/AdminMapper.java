package top.zekee.acmerbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.zekee.acmerbackend.pojo.CFUser;
import top.zekee.acmerbackend.pojo.Contest;
import top.zekee.acmerbackend.pojo.Problem;
import top.zekee.acmerbackend.pojo.ProblemStatistic;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AdminMapper {

    void updateProblems(List<Problem> problems);

    void updateProblemStatistics(List<ProblemStatistic> problemStatistics);

    void updateContests(List<Contest> contests);

    void updateCFUsers(List<CFUser> cfUsers);

}
