package top.zekee.acmerbackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zekee.acmerbackend.pojo.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminCFUserVo {
    private Integer id;
    private String handle;
    private String email;
    private String vkId;
    private String openId;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String organization;
    private Integer contribution;
    private String rank;
    private Integer rating;
    private String maxRank;
    private Integer maxRating;
    private Long lastOnlineTimeSeconds;
    private Long registrationTimeSeconds;
    private Integer friendOfCount;
    private String avatar;
    private String titlePhoto;
    private Integer holder;
    private Integer accountType;
    private User user;
}
