package top.zekee.acmerbackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zekee.acmerbackend.pojo.Contest;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContestsVo {
    ArrayList<Contest> contests = new ArrayList<>();
}
