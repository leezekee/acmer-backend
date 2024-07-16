package top.zekee.acmerbackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class SubmissionVo {
    Integer all;
    Integer acInContest;
    Integer acInMakeup;
    Integer waInContest;
    Integer waInMakeup;

    public SubmissionVo(){
        all = 0;
        acInContest = 0;
        acInMakeup = 0;
        waInContest = 0;
        waInMakeup = 0;
    }

    public void addAll(){
        all++;
    }

    public void addAcInContest(){
        acInContest++;
        addAll();
    }

    public void addAcInMakeup(){
        acInMakeup++;
        addAll();
    }

    public void addWaInContest(){
        waInContest++;
        addAll();
    }

    public void addWaInMakeup(){
        waInMakeup++;
        addAll();
    }


}
