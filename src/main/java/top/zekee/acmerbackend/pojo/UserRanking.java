package top.zekee.acmerbackend.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRanking {
    private Integer id;
    private Integer ranking;
    private String school;
    private String clazz;
    private String username;
    private Integer contestNumber;
    private Integer acceptNumber;
    private Integer makeupNumber;
    private String owner;
    private String accountType;
}
