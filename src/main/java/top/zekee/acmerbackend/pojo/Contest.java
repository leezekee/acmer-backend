package top.zekee.acmerbackend.pojo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Contest {
    private Integer id;
    private String name;
    private String startTime;
    private String duration;
    private Integer participants;
}
