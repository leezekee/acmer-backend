package top.zekee.acmerbackend.utils;

import org.springframework.web.client.RestTemplate;
import top.zekee.acmerbackend.dto.CFProblemDto;
import top.zekee.acmerbackend.dto.ContestDto;
import top.zekee.acmerbackend.pojo.ProblemStatistic;
import top.zekee.acmerbackend.pojo.Problem;
import top.zekee.acmerbackend.vo.ContestsVo;
import top.zekee.acmerbackend.vo.ProblemsVo;

public class SpiderUtil {
    static String PROBLEM_URL = "https://codeforces.com/api/problemset.problems";
    static String CONTEST_URL = "https://codeforces.com/api/contest.list";

    public ProblemsVo getCFProblems() {
        RestTemplate restTemplate = new RestTemplate();
        CFProblemDto result = restTemplate.getForObject(PROBLEM_URL, CFProblemDto.class);
        ProblemsVo response = new ProblemsVo();

        if (result == null || !result.getStatus().equals("OK")) {
            return null;
        }

        for (CFProblemDto.Problem problem : result.getResult().getProblems()) {
            Problem newProblem = new Problem();
            newProblem.setContestId(problem.getContestId());
            newProblem.setIndex(problem.getIndex());
            newProblem.setName(problem.getName());
            newProblem.setType(problem.getType());
            newProblem.setPoints(problem.getPoints());
            newProblem.setRating(problem.getRating());
            StringBuilder tags = new StringBuilder();
            for (String tag : problem.getTags()) {
                tags.append(tag).append(";");
            }
            newProblem.setTags(tags.toString());
            response.addProblem(newProblem);
        }

        for (CFProblemDto.ProblemStatistic problemStatistic : result.getResult().getProblemStatistics()) {
            ProblemStatistic newProblemStatistic = new ProblemStatistic();
            newProblemStatistic.setContestId(problemStatistic.getContestId());
            newProblemStatistic.setIndex(problemStatistic.getIndex());
            newProblemStatistic.setSolvedCount(problemStatistic.getSolvedCount());
            response.addProblemStatistic(newProblemStatistic);
        }

        return response;
    }

    public ContestsVo getCFContests() {
        RestTemplate restTemplate = new RestTemplate();
        ContestDto result = restTemplate.getForObject(CONTEST_URL, ContestDto.class);

        if (result == null || !result.getStatus().equals("OK")) {
            return null;
        }

        ContestsVo response = new ContestsVo();
        response.setContests(result.getResult());

        return response;
    }
}
