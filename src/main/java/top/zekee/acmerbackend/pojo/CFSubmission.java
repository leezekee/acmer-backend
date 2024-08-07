package top.zekee.acmerbackend.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CFSubmission {
    private Integer contestId;
    private Long creationTimeSeconds;
    private Integer problemId;
    private String handle;
    private String participantType;
    private String programmingLanguage;
    private String verdict;
    private Integer passedTestCount;
    private Long timeConsumedMillis;
    private String problemIndex;
}
