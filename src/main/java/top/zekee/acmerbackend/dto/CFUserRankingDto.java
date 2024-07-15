package top.zekee.acmerbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zekee.acmerbackend.pojo.CFUserRanking;
import top.zekee.acmerbackend.vo.CFUserRankingVo;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CFUserRankingDto {
    String status;
    List<CFUserRanking> result;
}
