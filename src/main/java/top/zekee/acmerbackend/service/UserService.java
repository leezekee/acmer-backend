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

    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    public int register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userMapper.addUser(user);
        return user.getId();
    }

    public User findUserById(Integer id) {
        return userMapper.getUserById(id);
    }
}
