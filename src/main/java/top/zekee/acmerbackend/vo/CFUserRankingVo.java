package top.zekee.acmerbackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zekee.acmerbackend.pojo.CFUserRanking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CFUserRankingVo {
    List<CFUserRanking> results = new ArrayList<>();

    public void addResults(List<CFUserRanking> cfUserRankings) {
        results.addAll(cfUserRankings);
    }
}
