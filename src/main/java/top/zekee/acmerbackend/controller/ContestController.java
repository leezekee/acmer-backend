package top.zekee.acmerbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zekee.acmerbackend.service.ContestService;

@RestController
@RequestMapping("/contest")
public class ContestController {
    ContestService contestService;

    @Autowired
    public ContestController(ContestService contestService) {
        this.contestService = contestService;
    }
}
