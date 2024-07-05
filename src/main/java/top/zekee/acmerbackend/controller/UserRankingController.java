package top.zekee.acmerbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zekee.acmerbackend.service.UserRankingSerivice;

@RestController
@RequestMapping("/cfRanking")
public class UserRankingController {
    UserRankingSerivice userRankingSerivice;

    @Autowired
    public UserRankingController(UserRankingSerivice userRankingSerivice) {
        this.userRankingSerivice = userRankingSerivice;
    }
}
