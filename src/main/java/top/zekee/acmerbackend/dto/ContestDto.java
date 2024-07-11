package top.zekee.acmerbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContestDto {
    @NotNull(message = "pageNum不能为空")
    Integer pageNum;
    @NotNull(message = "pageSize不能为空")
    Integer pageSize;
    Integer id;                  // 比赛ID
    String name;                 // 比赛名称
    Long startTimeSeconds;       // 开始时间
    Long durationSeconds;         // 持续时间
    Long relativeTimeSeconds;    // 相对时间
    String phase;                // 比赛阶段
    String type;                 // 比赛类型
    Boolean frozen;              // 是否冻结
}
