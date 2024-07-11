package top.zekee.acmerbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data
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
}
