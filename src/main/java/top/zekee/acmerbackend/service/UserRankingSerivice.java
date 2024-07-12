package top.zekee.acmerbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zekee.acmerbackend.mapper.UserRankingMapper;
import top.zekee.acmerbackend.pojo.CFUser;
import top.zekee.acmerbackend.vo.UserRankVo;

import java.util.List;

@Service
public class UserRankingSerivice {
    UserRankingMapper userRankingMapper;
    UserService userService;

    @Autowired
    public UserRankingSerivice(UserRankingMapper userRankingMapper, UserService userService) {
        this.userRankingMapper = userRankingMapper;
        this.userService = userService;
    }

    public List<UserRankVo> getUserRankingList() {
        return userRankingMapper.findRankList();
    }
}
