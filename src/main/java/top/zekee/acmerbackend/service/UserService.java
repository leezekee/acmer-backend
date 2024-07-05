package top.zekee.acmerbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zekee.acmerbackend.mapper.UserMapper;
import top.zekee.acmerbackend.pojo.User;

@Service
public class UserService {
    UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getUserById(Integer id) {

        return userMapper.getUserById(id);
    }
}
