package top.zekee.acmerbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.zekee.acmerbackend.dto.ProblemFilterDto;
import top.zekee.acmerbackend.pojo.Problem;

import java.util.List;

@Mapper
public interface ProblemMapper {
    List<Problem> findProblems(ProblemFilterDto problemFilterDto);

    Problem findProblemByContestIdAndIndex(Integer contestId, String index);
}
