package top.zekee.acmerbackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CFProblemVo {
    ArrayList<ProblemStatistic> problemStatistics = new ArrayList<>();
    ArrayList<Problem> problems = new ArrayList<>();

    public void addProblem(Problem problem){
        problems.add(problem);
    }

    public void addProblemStatistic(ProblemStatistic problemStatistic){
        problemStatistics.add(problemStatistic);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProblemStatistic {
        Integer contestId;
        String index;
        Integer solvedCount;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Problem{
        Integer contestId;
        String index;
        String name;
        String type;
        Float points;
        Integer rating;
        ArrayList<String> tags;
    }
}
