package top.zekee.acmerbackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zekee.acmerbackend.pojo.CFSubmission;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CFSubmissionVo {
    List<CFSubmission> submissions = new ArrayList<>();

    public void addSubmission(CFSubmission submission) {
        submissions.add(submission);
    }
}
