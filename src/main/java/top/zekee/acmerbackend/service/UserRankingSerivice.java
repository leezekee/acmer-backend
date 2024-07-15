package top.zekee.acmerbackend.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zekee.acmerbackend.mapper.UserRankingMapper;
import top.zekee.acmerbackend.pojo.CFUser;
import top.zekee.acmerbackend.pojo.PageBean;
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

    public PageBean<UserRankVo> getUserRankingList(Integer pageNum, Integer pageSize) {
        PageBean<UserRankVo> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        List<UserRankVo> userRankVos = userRankingMapper.findRankList();
        Page<UserRankVo> page = (Page<UserRankVo>) userRankVos;
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }
}
