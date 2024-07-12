package top.zekee.acmerbackend.dto;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CFAccountDto {
    @Parameter(description = "Codeforces用户名")
    @NotBlank(message = "Codeforces用户名不能为空")
    String handle;
}
