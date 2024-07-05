package top.zekee.acmerbackend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zekee.acmerbackend.mapper.ProblemMapper;

@Service
public class ProblemService {
    ProblemMapper problemMapper;

    @Autowired
    public ProblemService(ProblemMapper problemMapper) {
        this.problemMapper = problemMapper;
    }
}
