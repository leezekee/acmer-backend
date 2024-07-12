package top.zekee.acmerbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.zekee.acmerbackend.pojo.CFUser;
import top.zekee.acmerbackend.vo.UserRankVo;

import java.util.List;

@Mapper
public interface UserRankingMapper {
    List<CFUser> findAll();

    List<UserRankVo> findRankList();
}
