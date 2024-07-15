package top.zekee.acmerbackend.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zekee.acmerbackend.pojo.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserVo {
    private Integer id;
    private String school;
    private String clazz;
    private String grade;
    private String name;
    private String award;
    private String username;
    private Integer cfRanking;
    private Integer Auth;

    public AdminUserVo(User user) {
        this.id = user.getId();
        this.school = user.getSchool();
        this.clazz = user.getClazz();
        this.grade = user.getGrade();
        this.name = user.getName();
        this.award = user.getAward();
        this.username = user.getUsername();
        this.cfRanking = user.getCfRanking();
        this.Auth = user.getAuth();
    }
}
