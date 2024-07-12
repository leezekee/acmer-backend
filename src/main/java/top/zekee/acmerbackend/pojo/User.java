package top.zekee.acmerbackend.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String school;
    private String clazz;
    private String grade;
    private String name;
    private String award;
    private String username;
    @JsonIgnore
    private String password;
    private String status;
    private Integer cfRanking;
    private Integer cfContest;
    private Integer cfAccept;
    private Integer cfMakeup;
    @JsonIgnore
    private Integer Auth;
}
