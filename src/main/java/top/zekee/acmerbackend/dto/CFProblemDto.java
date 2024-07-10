package top.zekee.acmerbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CFProblemDto {
    String status;
    Result result;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Result {
        ArrayList<Problem> problems = new ArrayList<>();
        ArrayList<ProblemStatistic> problemStatistics = new ArrayList<>();
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
