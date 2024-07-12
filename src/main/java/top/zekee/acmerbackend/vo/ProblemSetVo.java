package top.zekee.acmerbackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zekee.acmerbackend.pojo.Problem;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemSetVo {
    List<Problem> problems;
}
