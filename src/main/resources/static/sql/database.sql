drop database if exists db_acmer;

create database db_acmer;

use db_acmer;

-- 用户表
CREATE TABLE tb_user
(
    id         INT PRIMARY KEY AUTO_INCREMENT, -- 用户ID
    school     VARCHAR(255),                   -- 学校
    clazz      VARCHAR(255),                   -- 班级
    grade      VARCHAR(255),                   -- 年级
    name       VARCHAR(255),                   -- 真实姓名
    award      VARCHAR(255),                   -- 奖项
    username   VARCHAR(255),                   -- 用户名
    status     VARCHAR(255),                   -- 用户状态
    cf_ranking INT,                            -- cf分数
    cf_contest INT,                            -- cf比赛次数
    cf_accept  INT,                            -- cf通过题数
    cf_makeup  INT,                            -- cf补题通过数
    auth       INT,                            -- 身份认证
    password   VARCHAR(255)                    -- 密码
);

-- 题目表
CREATE TABLE tb_problems
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    contestId INT,
    `index`   VARCHAR(255),
    name      VARCHAR(255),
    `type`    VARCHAR(255),
    points    FLOAT,
    rating    INT,
    tags      VARCHAR(255)
);

-- 比赛表
CREATE TABLE tb_contest
(
    id                  INT PRIMARY KEY, -- 比赛ID
    name                VARCHAR(255),                   -- 比赛名称
    startTimeSeconds    BIGINT,                         -- 开始时间
    durationSeconds      BIGINT,                         -- 持续时间
    relativeTimeSeconds BIGINT,                         -- 相对时间
    phase               VARCHAR(255),                   -- 比赛阶段
    type                VARCHAR(255),                   -- 比赛类型
    frozen              BOOL                            -- 是否冻结
);

-- 用户排名表
CREATE TABLE tb_user_ranking
(
    id             INT PRIMARY KEY AUTO_INCREMENT, -- 排名ID
    ranking        INT,                            -- 分数
    school         VARCHAR(255),                   -- 学校
    class          VARCHAR(255),                   -- 班级
    username       VARCHAR(255),                   -- 用户名
    contest_number INT,                            -- 比赛次数
    accept_number  INT,                            -- 解题数
    makeup_number  INT,                            -- 补题数
    owner          VARCHAR(255),                   -- 账户持有者
    account_type   VARCHAR(255)                    -- 帐号类型
);

-- 题目统计数据
CREATE TABLE tb_problem_statistics
(
    id           INT PRIMARY KEY AUTO_INCREMENT, -- 统计ID
    contest_id   INT,
    `index`      VARCHAR(10),
    solved_count INT
);

CREATE TABLE tb_cf_user
(
    id                      INT PRIMARY KEY AUTO_INCREMENT,
    handle                  VARCHAR(255) NOT NULL,
    email                   VARCHAR(255),
    vkId                    VARCHAR(255),
    openId                  VARCHAR(255),
    firstName               VARCHAR(255),
    lastName                VARCHAR(255),
    country                 VARCHAR(255),
    city                    VARCHAR(255),
    organization            VARCHAR(255),
    contribution            INT,
    `rank`                  VARCHAR(255),
    rating                  INT,
    maxRank                 VARCHAR(255),
    maxRating               INT,
    lastOnlineTimeSeconds   BIGINT,
    registrationTimeSeconds BIGINT,
    friendOfCount           INT,
    avatar                  VARCHAR(255),
    titlePhoto              VARCHAR(255)
);

CREATE TABLE tb_cf_user_and_user
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    user_id    INT,
    cf_user_id INT
);









