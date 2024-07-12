package top.zekee.acmerbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.zekee.acmerbackend.dto.ContestFilterDto;
import top.zekee.acmerbackend.pojo.Contest;

import java.util.List;

@Mapper
public interface ContestMapper {
    List<Contest> findContests(ContestFilterDto contestDto);
}
