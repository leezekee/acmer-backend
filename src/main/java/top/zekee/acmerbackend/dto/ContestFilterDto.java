package top.zekee.acmerbackend.dto;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zekee.acmerbackend.anno.EnumValue;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContestFilterDto {
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

    @EnumValue(enumClass = OrderByEnum.class, enumMethod = "isValid")
    @Parameter(description = "排序方式, 可选值: id, name, start_time_seconds, duration_seconds, relative_time_seconds, phase, type, frozen")
    String orderBy;              // 排序方式

    @EnumValue(enumClass = OrderEnum.class, enumMethod = "isValid")
    @Parameter(description = "排序方式, 可选值: asc, desc")
    String order;                // 排序方式

    public enum OrderByEnum {
        id,
        name,
        start_time_seconds,
        duration_seconds,
        relative_time_seconds,
        phase,
        type,
        frozen;

        public static boolean isValid(String name) {
            for (OrderByEnum userStatusEnum : OrderByEnum.values()) {
                if (userStatusEnum.name().equals(name)) {
                    return true;
                }
            }
            return false;
        }
    }

    public enum OrderEnum {
        asc,
        desc;

        public static boolean isValid(String name) {
            for (OrderEnum userStatusEnum : OrderEnum.values()) {
                if (userStatusEnum.name().equals(name)) {
                    return true;
                }
            }
            return false;
        }
    }
}
