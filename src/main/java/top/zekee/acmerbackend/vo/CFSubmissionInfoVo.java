package top.zekee.acmerbackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CFSubmissionInfoVo {
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
    private Integer accountType;
}
