package top.zekee.acmerbackend.pojo;


import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Problem {
    Integer id;
    Integer contestId;
    String index;
    String name;
    String type;
    Float points;
    Integer rating;
    String tags;
}
