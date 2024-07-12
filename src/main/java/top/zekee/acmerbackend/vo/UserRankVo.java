package top.zekee.acmerbackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zekee.acmerbackend.pojo.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRankVo {
    Integer id;
    Integer accountType;
    String handle;
    Integer holder;
    Integer rating;
    User user;
}
