package top.zekee.acmerbackend.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CFSubmission {
    private Integer id;
    private Integer contestId;
    private Integer creationTimeSeconds;
    private Integer relativeTimeSeconds;
    private Problem problem;
//    private Party author;
    private String programmingLanguage;
    private Verdict verdict;
    private Testset testset;
    private Integer passedTestCount;
    private Integer timeConsumedMillis;
    private Integer memoryConsumedBytes;
    private Double points;

    public enum Verdict {
        FAILED, OK, PARTIAL, COMPILATION_ERROR, RUNTIME_ERROR, WRONG_ANSWER,
        PRESENTATION_ERROR, TIME_LIMIT_EXCEEDED, MEMORY_LIMIT_EXCEEDED, IDLENESS_LIMIT_EXCEEDED,
        SECURITY_VIOLATED, CRASHED, INPUT_PREPARATION_CRASHED, CHALLENGED, SKIPPED, TESTING, REJECTED
    }

    public enum Testset {
        SAMPLES, PRETESTS, TESTS, CHALLENGES, TESTS1, TESTS2, TESTS3, TESTS4, TESTS5,
        TESTS6, TESTS7, TESTS8, TESTS9, TESTS10
    }
}
