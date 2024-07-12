package top.zekee.acmerbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zekee.acmerbackend.vo.UserVo;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    private String school;
    private String clazz;
    private String grade;
    private String name;
    private String password;
    private String award;
}
