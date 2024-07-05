package top.zekee.acmerbackend.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CFProblem {
    private Integer contestId;
    private String problemsetName;
    private String index;
    private String name;
    private ProblemType type;
    private Double points;
    private Integer rating;
    private List<String> tags;

    public enum ProblemType {
        PROGRAMMING, QUESTION
    }
}
