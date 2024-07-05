package top.zekee.acmerbackend.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CFContest {
    private Integer id;
    private String name;
    private ContestType type;
    private ContestPhase phase;
    private Boolean frozen;
    private Integer durationSeconds;
    private Long startTimeSeconds;
    private Integer relativeTimeSeconds;
    private String preparedBy;
    private String websiteUrl;
    private String description;
    private Integer difficulty;
    private String kind;
    private String icpcRegion;
    private String country;
    private String city;
    private String season;

    public enum ContestType {
        CF, IOI, ICPC
    }

    public enum ContestPhase {
        BEFORE, CODING, PENDING_SYSTEM_TEST, SYSTEM_TEST, FINISHED
    }
}

