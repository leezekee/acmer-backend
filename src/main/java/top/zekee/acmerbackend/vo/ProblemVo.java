package top.zekee.acmerbackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemVo {
    Integer id;
    Integer contestId;
    String index;
    String name;
    String type;
    Float points;
    Integer rating;
    List<String> tags;
}
