package top.zekee.acmerbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.zekee.acmerbackend.pojo.Problem;
import top.zekee.acmerbackend.vo.ProblemSetVo;

import java.util.List;

@Mapper
public interface TrainMapper {
    List<Problem> getRandomProblems(int min, int max);
}
