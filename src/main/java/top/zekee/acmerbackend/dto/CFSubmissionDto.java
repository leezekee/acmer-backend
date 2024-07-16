package top.zekee.acmerbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import top.zekee.acmerbackend.pojo.CFProblem;
import top.zekee.acmerbackend.pojo.CFSubmission;
import top.zekee.acmerbackend.pojo.Problem;

import java.util.List;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CFSubmissionDto {
    String status;
    List<CFSubmissionDtoResult> result;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CFSubmissionDtoResult {
        private Integer contestId;
        private Long creationTimeSeconds;
        private Long relativeTimeSeconds;
        private CFProblemDto.Problem problem;
        private Party author;
        private String programmingLanguage;
        private String verdict;
        private Integer passedTestCount;
        private Long timeConsumedMillis;
        private Long memoryConsumedBytes;
        private Double points;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Party {
            private Integer contestId;
            private List<Member> members;
            private String participantType;
            private Integer teamId;
            private String teamName;
            private Boolean ghost;
            private Integer room;
            private Integer startTimeSeconds;

        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Member {
            private String handle;
            private String name;
        }

        public CFSubmission toSubmission() {
            CFSubmission submission = new CFSubmission();
            submission.setContestId(this.contestId);
            submission.setCreationTimeSeconds(this.creationTimeSeconds);
            submission.setHandle(this.author.getMembers().get(0).getHandle());
            submission.setParticipantType(this.author.getParticipantType());
            submission.setProgrammingLanguage(this.programmingLanguage);
            submission.setVerdict(this.verdict);
            submission.setPassedTestCount(this.passedTestCount);
            submission.setProblemIndex(this.problem.getIndex());

//            log.info(submission.toString());
            return submission;
        }
    }
}