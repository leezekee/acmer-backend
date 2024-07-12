package top.zekee.acmerbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zekee.acmerbackend.mapper.UserMapper;
import top.zekee.acmerbackend.pojo.CFUser;
import top.zekee.acmerbackend.pojo.User;

import java.util.List;

@Service
public class UserService {
    UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void addCFAccount(Integer holder, String handle) {
        List<CFUser> cfUsers = userMapper.findCFAccountByHolder(holder);
        if (cfUsers.isEmpty()) {
            userMapper.addCFAccount(holder, handle, 1);
        } else {
            userMapper.addCFAccount(holder, handle, 0);
        }
    }

    public CFUser findCFAccountByHandle(String handle) {
        return userMapper.findCFAccountByHandle(handle);
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
        return userMapper.findUserById(id);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    public void deleteCFAccount(String cfUsername) {
        userMapper.deleteCFAccount(cfUsername);
    }

    public void setMainCFAccount(Integer id, String cfUsername) {
        List<CFUser> cfUsers = userMapper.findCFAccountByHolder(id);
        for (CFUser cfUser : cfUsers) {
            if (cfUser.getHandle().equals(cfUsername)) {
                cfUser.setAccountType(1);
            } else {
                cfUser.setAccountType(0);
            }
        }
        userMapper.updateAccountType(cfUsers);
    }

    public List<CFUser> findCFACCountByHolder(Integer id) {
        return userMapper.findCFAccountByHolder(id);
    }

    public List<CFUser> findAll() {
        return userMapper.findAll();
    }

    public void updateUserRankById(Integer id, Integer ranking) {
        userMapper.updateUserRankById(id, ranking);
    }
}
