package top.zekee.acmerbackend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemStatistic {
    Integer id;
    Integer contestId;
    String index;
    Integer solvedCount;
}
