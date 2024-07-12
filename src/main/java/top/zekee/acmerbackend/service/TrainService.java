package top.zekee.acmerbackend.service;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import top.zekee.acmerbackend.auth.UserContext;
import top.zekee.acmerbackend.mapper.TrainMapper;
import top.zekee.acmerbackend.pojo.Problem;
import top.zekee.acmerbackend.pojo.Response;
import top.zekee.acmerbackend.pojo.User;
import top.zekee.acmerbackend.vo.ProblemSetVo;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainService {
    TrainMapper trainMapper;

    @Autowired
    public TrainService(TrainMapper trainMapper, UserContext userContext) {
        this.trainMapper = trainMapper;
    }


    public List<ProblemSetVo> getProblemSet(Integer setNum, int min, int max) {
        List<ProblemSetVo> problemSetVos = new ArrayList<>();
        for (int i = 0; i < setNum; i++) {
            List<Problem> problems = trainMapper.getRandomProblems(min, max);
            problemSetVos.add(new ProblemSetVo(problems));
        }
        return problemSetVos;
    }
}
