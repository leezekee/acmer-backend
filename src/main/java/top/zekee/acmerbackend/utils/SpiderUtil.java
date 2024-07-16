package top.zekee.acmerbackend.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import top.zekee.acmerbackend.dto.*;
import top.zekee.acmerbackend.pojo.CFSubmission;
import top.zekee.acmerbackend.pojo.ProblemStatistic;
import top.zekee.acmerbackend.pojo.Problem;
import top.zekee.acmerbackend.vo.*;

import java.util.List;

@Slf4j
public class SpiderUtil {
    static String PROBLEM_URL = "https://codeforces.com/api/problemset.problems";
    static String CONTEST_URL = "https://codeforces.com/api/contest.list";
    static String USER_INFO_URL = "https://codeforces.com/api/user.info?handles=";
    static String USER_RANKING_URL = "https://codeforces.com/api/user.rating?handle=";
    static String USER_SUBMISSION_URL = "https://codeforces.com/api/user.status?handle=";

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
        CFContestDto result = restTemplate.getForObject(CONTEST_URL, CFContestDto.class);

        if (result == null || !result.getStatus().equals("OK")) {
            return null;
        }

        ContestsVo response = new ContestsVo();
        response.setContests(result.getResult());

        return response;
    }

    public CFUserInfoVo getCFUserInfo(List<String> handles) {
        StringBuilder params = new StringBuilder();
        for (String handle : handles) {
            params.append(handle).append(";");
        }

        RestTemplate restTemplate = new RestTemplate();
        CFUserInfoDto result = restTemplate.getForObject(USER_INFO_URL + params, CFUserInfoDto.class);

        if (result == null || !result.getStatus().equals("OK")) {
            return null;
        }

        CFUserInfoVo response = new CFUserInfoVo();
        response.setCfUsers(result.getResult());

        return response;
    }

    public CFUserRankingVo getCFUserRanking(List<String> handles)  {
        RestTemplate restTemplate = new RestTemplate();
        CFUserRankingVo cfUserRankingVo = new CFUserRankingVo();
        for (String handle : handles) {
            CFUserRankingDto result = restTemplate.getForObject(USER_RANKING_URL + handle, CFUserRankingDto.class);
            if (result == null || !result.getStatus().equals("OK")) {
                return null;
            }
            cfUserRankingVo.addResults(result.getResult());
        }
        return cfUserRankingVo;
    }

    public CFSubmissionVo getCFSubmissions(List<String> handles) {
        RestTemplate restTemplate = new RestTemplate();
        CFSubmissionVo cfSubmissionVo = new CFSubmissionVo();
        for (String handle : handles) {
            CFSubmissionDto result = restTemplate.getForObject(USER_SUBMISSION_URL + handle, CFSubmissionDto.class);
            if (result == null || !result.getStatus().equals("OK")) {
                return null;
            }
//            log.info(result.toString());
            for (CFSubmissionDto.CFSubmissionDtoResult submissionDtoResult : result.getResult()) {
                CFSubmission submission = submissionDtoResult.toSubmission();
                cfSubmissionVo.addSubmission(submission);
            }
        }
        return cfSubmissionVo;
    }
}
