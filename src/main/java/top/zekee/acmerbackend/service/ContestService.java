package top.zekee.acmerbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zekee.acmerbackend.mapper.ContestMapper;

@Service
public class ContestService {
    ContestMapper contestMapper;

    @Autowired
    public ContestService(ContestMapper contestMapper) {
        this.contestMapper = contestMapper;
    }
}
