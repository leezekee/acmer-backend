package top.zekee.acmerbackend.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Contest {
    private Integer id;
    private String name;
    private String startTime;
    private String duration;
    private Integer participants;
}
