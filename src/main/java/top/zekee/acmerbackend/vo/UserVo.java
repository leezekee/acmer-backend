package top.zekee.acmerbackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zekee.acmerbackend.dto.UserUpdateDto;
import top.zekee.acmerbackend.pojo.CFUser;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {

    private Integer id;
    private String school;
    private String clazz;
    private String grade;
    private String name;
    private String username;
    private String password;
    private CFAccount cfAccount;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CFAccount {
        CFUser mainAccount = new CFUser();
        List<CFUser> subAccount = new ArrayList<>();
    }

}
