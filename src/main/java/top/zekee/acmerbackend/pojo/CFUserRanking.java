package top.zekee.acmerbackend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CFUserRanking {
    private int id;
    private int contestId;
    private String contestName;
    private String handle;
    private int rank;
    private long ratingUpdateTimeSeconds;
    private int oldRating;
    private int newRating;
}
