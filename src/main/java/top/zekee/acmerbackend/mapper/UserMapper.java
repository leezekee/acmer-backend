package top.zekee.acmerbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.zekee.acmerbackend.pojo.User;

@Mapper
public interface UserMapper {
    public User getUserById(Integer id);
}
