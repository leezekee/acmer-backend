package top.zekee.acmerbackend.pojo;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Problem {
    private Integer id;
    private String contestType;
    private String problemNumber;
    private String problemTitle;
    private Integer difficulty;
    private String tags;

}
