package top.zekee.acmerbackend.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CFUser {
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
}