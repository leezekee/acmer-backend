package top.zekee.acmerbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zekee.acmerbackend.pojo.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserDto {
    private Integer id;
    private String school;
    private String clazz;
    private String grade;
    private String name;
    private String award;
    private String username;
    private Integer cfRanking;
    private Integer Auth;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setSchool(school);
        user.setClazz(clazz);
        user.setGrade(grade);
        user.setName(name);
        user.setAward(award);
        user.setCfRanking(cfRanking);
        user.setAuth(Auth);
        return user;
    }
}
