package top.zekee.acmerbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zekee.acmerbackend.mapper.TrainMapper;

@Service
public class TrainService {
    TrainMapper trainMapper;

    @Autowired
    public TrainService(TrainMapper trainMapper) {
        this.trainMapper = trainMapper;
    }
}
