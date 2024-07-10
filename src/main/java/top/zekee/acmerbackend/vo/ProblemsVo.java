package top.zekee.acmerbackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zekee.acmerbackend.pojo.Problem;
import top.zekee.acmerbackend.pojo.ProblemStatistic;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemsVo {
    ArrayList<ProblemStatistic> problemStatistics = new ArrayList<>();
    ArrayList<Problem> problems = new ArrayList<>();

    public void addProblem(Problem problem){
        problems.add(problem);
    }

    public void addProblemStatistic(ProblemStatistic problemStatistic){
        problemStatistics.add(problemStatistic);
    }

}
