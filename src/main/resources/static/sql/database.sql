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
    id        INT UNIQUE AUTO_INCREMENT,
    contest_id INT,
    `index`   VARCHAR(255),
    name      VARCHAR(255),
    `type`    VARCHAR(255),
    points    FLOAT,
    rating    INT,
    tags      VARCHAR(255),
    primary key (contest_id, `index`)
);

-- 比赛表
CREATE TABLE tb_contest
(
    id                  INT PRIMARY KEY, -- 比赛ID
    name                VARCHAR(255),                   -- 比赛名称
    start_time_seconds    BIGINT,                         -- 开始时间
    duration_seconds      BIGINT,                         -- 持续时间
    relative_time_seconds BIGINT,                         -- 相对时间
    phase               VARCHAR(255),                   -- 比赛阶段
    type                VARCHAR(255),                   -- 比赛类型
    frozen              BOOL                            -- 是否冻结
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
    handle                  VARCHAR(255) NOT NULL PRIMARY KEY,
    email                   VARCHAR(255),
    vkId                    VARCHAR(255),
    openId                  VARCHAR(255),
    first_ame               VARCHAR(255),
    last_ame                VARCHAR(255),
    country                 VARCHAR(255),
    city                    VARCHAR(255),
    organization            VARCHAR(255),
    contribution            INT,
    `rank`                  VARCHAR(255),
    rating                  INT,
    max_rank                 VARCHAR(255),
    max_rating               INT,
    last_online_time_seconds   BIGINT,
    registration_time_seconds BIGINT,
    friend_of_count           INT,
    avatar                  VARCHAR(255),
    title_photo              VARCHAR(255),
    holder                  VARCHAR(255),
    account_type            INT
);

CREATE TABLE tb_cf_user_rating
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    contest_id INT,
    contest_name VARCHAR(255),
    handle VARCHAR(255),
    `rank` INT,
    rating_update_time_seconds BIGINT,
    old_rating INT,
    new_rating INT
);

CREATE TABLE tb_cf_submission
(
    contest_id INT,
    creation_time_seconds BIGINT,
    problem_id INT,
    handle VARCHAR(255),
    participant_type VARCHAR(255),
    programming_language VARCHAR(255),
    verdict VARCHAR(255),
    time_consumed_millis BIGINT,
    passed_test_count INT,
    PRIMARY KEY (contest_id, handle, problem_id, participant_type, creation_time_seconds)
);









