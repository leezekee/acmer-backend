drop database if exists db_acmer;

create database db_acmer;

use db_acmer;

-- 用户表
CREATE TABLE tb_user
(
    id         INT PRIMARY KEY AUTO_INCREMENT, -- 用户ID
    school     VARCHAR(255),                   -- 学校
    class      VARCHAR(255),                   -- 班级
    grade      VARCHAR(255),                   -- 年级
    name       VARCHAR(255),                   -- 真实姓名
    award      VARCHAR(255),                   -- 奖项
    username   VARCHAR(255),                   -- 用户名
    status     VARCHAR(255),                   -- 用户状态
    cf_ranking INT,                            -- cf分数
    cf_contest INT,                            -- cf比赛次数
    cf_accept  INT,                            -- cf通过题数
    cf_makeup  INT                             -- cf补题通过数
);

-- 题目表
CREATE TABLE tb_problem
(
    id             INT PRIMARY KEY AUTO_INCREMENT, -- 题目ID
    contest_type   VARCHAR(255),                   -- 比赛类型
    problem_number VARCHAR(255),                   -- 题目编号
    problem_title  VARCHAR(255),                   -- 题目名称
    difficulty     INT,                            -- 题目难度
    tags           VARCHAR(255)                    -- 题目标签
);

-- 比赛表
CREATE TABLE tb_contest
(
    id           INT PRIMARY KEY AUTO_INCREMENT, -- 比赛ID
    name         VARCHAR(255),                   -- 比赛名称
    startTime    DATETIME,                       -- 开始时间
    duration     VARCHAR(255),                   -- 持续时间
    participants INT                             -- 参与人数
);

-- 用户排名表
CREATE TABLE tb_user_ranking
(
    id             INT PRIMARY KEY AUTO_INCREMENT, -- 排名ID
    ranking        INT,                            -- 分数
    school         VARCHAR(255),                   -- 学校
    class          VARCHAR(255),                   -- 班级
    username       VARCHAR(255),                   -- 用户名
    contest_number   INT,                            -- 比赛次数
    accept_number INT,                            -- 解题数
    makeup_number INT,                            -- 补题数
    owner          VARCHAR(255),                   -- 账户持有者
    account_type    VARCHAR(255)                    -- 帐号类型
);









