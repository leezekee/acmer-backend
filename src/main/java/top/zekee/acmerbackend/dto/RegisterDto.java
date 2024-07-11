package top.zekee.acmerbackend.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    @NotBlank(message = "用户名不能为空")
    String username;

    @NotBlank(message = "密码不能为空")
    String password;

    @NotBlank(message = "确认密码不能为空")
    String rePassword;
}
