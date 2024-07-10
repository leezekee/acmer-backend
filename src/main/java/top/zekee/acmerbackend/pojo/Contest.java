package top.zekee.acmerbackend.pojo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Contest {
    private Integer id;                  // 比赛ID
    private String name;                 // 比赛名称
    private Long startTimeSeconds;       // 开始时间
    private Long durationSeconds;         // 持续时间
    private Long relativeTimeSeconds;    // 相对时间
    private String phase;                // 比赛阶段
    private String type;                 // 比赛类型
    private Boolean frozen;              // 是否冻结
}
