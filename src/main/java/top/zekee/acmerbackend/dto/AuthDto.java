package top.zekee.acmerbackend.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {
    @NotBlank(groups = {Login.class, Register.class}, message = "用户名不能为空")
    String username;

    @NotBlank(groups = {Login.class, Register.class}, message = "密码不能为空")
    String password;

    @NotBlank(groups = {Register.class}, message = "确认密码不能为空")
    String rePassword;

    public interface Login {}
    public interface Register {}
}
