package top.zekee.acmerbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.zekee.acmerbackend.pojo.Contest;
import top.zekee.acmerbackend.pojo.Problem;
import top.zekee.acmerbackend.pojo.ProblemStatistic;

import java.util.ArrayList;

@Mapper
public interface AdminMapper {

    void updateProblems(ArrayList<Problem> problems);

    void updateProblemStatistics(ArrayList<ProblemStatistic> problemStatistics);

    void updateContests(ArrayList<Contest> contests);
}
