package top.zekee.acmerbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.zekee.acmerbackend.pojo.User;

@Mapper
public interface UserMapper {
    User getUserById(Integer id);

    void addUser(User user);

    User findUserByUsername(String username);
}
