package top.zekee.acmerbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.zekee.acmerbackend.pojo.CFUser;
import top.zekee.acmerbackend.pojo.User;

import java.util.List;

@Mapper
public interface UserMapper {
    User findUserById(Integer id);

    void addUser(User user);

    User findUserByUsername(String username);

    void updateUser(User user);

    CFUser findCFAccountByHandle(String handle);

    void addCFAccount(Integer holder, String handle, Integer accountType);

    void deleteCFAccount(String handle);

    List<CFUser> findCFAccountByHolder(Integer holder);

    void updateAccountType(List<CFUser> cfUsers);

    List<CFUser> findAll();

    void updateUserRankById(Integer id, Integer ranking);
}
