package top.zekee.acmerbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zekee.acmerbackend.mapper.UserRankingMapper;

@Service
public class UserRankingSerivice {
    UserRankingMapper userRankingMapper;

    @Autowired
    public UserRankingSerivice(UserRankingMapper userRankingMapper) {
        this.userRankingMapper = userRankingMapper;
    }
}
