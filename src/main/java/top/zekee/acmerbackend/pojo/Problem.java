package top.zekee.acmerbackend.pojo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Problem {
    private Integer id;
    private String contestType;
    private String problemNumber;
    private String problemTitle;
    private Integer difficulty;
    private String tags;

}
