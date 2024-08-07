package top.zekee.acmerbackend.dto;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import top.zekee.acmerbackend.anno.EnumValue;

@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ProblemFilterDto {
    @NotNull(message = "pageNum不能为空")
    Integer pageNum;
    @NotNull(message = "pageSize不能为空")
    Integer pageSize;

    Integer id;
    Integer contestId;
    String index;
    String name;
    String type;
    Float points;
    Integer rating;
    String tags;

    @EnumValue(enumClass = OrderByEnum.class, enumMethod = "isValid")
    @Parameter(description = "排序方式, 可选值: id, contest_id, index, name, type, points, rating, tags")
    String orderBy;

    @EnumValue(enumClass = OrderEnum.class, enumMethod = "isValid")
    @Parameter(description = "排序方式, 可选值: asc, desc")
    String order;                // 排序方式

    public enum OrderByEnum {
        id,
        contest_id,
        index,
        name,
        type,
        points,
        rating,
        tags;

        public static boolean isValid(String name) {
            for (OrderByEnum userStatusEnum : OrderByEnum.values()) {
                log.info("userStatusEnum: {}", userStatusEnum);
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
