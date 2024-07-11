package top.zekee.acmerbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.zekee.acmerbackend.dto.ContestDto;
import top.zekee.acmerbackend.pojo.Contest;

import java.util.List;

@Mapper
public interface ContestMapper {
    List<Contest> findContests(ContestDto contestDto);
}
