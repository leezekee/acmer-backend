# Acmer-backend

这是生产实习项目——ACM竞赛数据展示系统的后端

前后端已经部署至线上

* [点击查看文档](https://acmer.leezekee.top/api/doc.html)

* [点击查看网页](https://acmer.leezekee.top)

该仓库主要为项目后端、爬虫及部署部分

爬虫部分在`utils/SpiderUtil`中

部署使用`nginx`进行反向代理，其中nginx配置为

```nginx
server {
    listen 443 ssl;
    server_name acmer.leezekee.top;

    ssl_certificate path/to/pem;
    ssl_certificate_key path/to/key;
    ssl_session_timeout 5m;
    ssl_protocols TLSv1.1 TLSv1.2 TLSv1.3;
    ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:HIGH:!aNULL:!MD5:!RC4:!DHE;
    ssl_prefer_server_ciphers on;

    location / {
        root /home/leezekee/acmer/dist;
        index index.html;
        try_files $uri $uri/ /index.html;
    }

    location /api {
        proxy_pass http://127.0.0.1:8080/api;
        proxy_redirect off;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header X-Forwarded-Host $host;
        proxy_set_header X-Forwarded-Port $server_port;
        access_log /path/to/log;
    }
}

server {
    listen 80;
    server_name acmer.leezekee.top;
    rewrite ^(.*)$ https://$host$1 permanent;
}

```

数据库结构为：

```sql
-- 用户表，存储用户信息和Codeforces相关数据
CREATE TABLE tb_user (
    id INT PRIMARY KEY AUTO_INCREMENT, -- 用户的唯一标识符
    school VARCHAR(255),               -- 用户所在的学校
    clazz VARCHAR(255),                -- 用户所在的班级
    grade VARCHAR(255),                -- 用户所在的年级
    name VARCHAR(255),                 -- 用户的真实姓名
    award VARCHAR(255),                -- 用户获得的奖项
    username VARCHAR(255),             -- 用户名，用于登录
    password VARCHAR(255)              -- 加密后的密码
    status VARCHAR(255),               -- 用户账户的状态（例如：激活、禁用）
    cf_ranking INT,                    -- Codeforces用户的排名
    cf_contest INT,                    -- 参加过的Codeforces比赛数量
    cf_accept INT,                     -- 在Codeforces上通过的问题数量
    cf_makeup INT,                     -- 补做的问题数量
    auth INT,                          -- 身份验证状态（例如：是否已验证邮箱）
);

-- 题目表，存储Codeforces比赛中的题目信息
CREATE TABLE tb_problems (
    id INT UNIQUE AUTO_INCREMENT,      -- 题目的唯一标识符
    contest_id INT,                    -- 题目所属的比赛ID
    `index` VARCHAR(255),              -- 题目在比赛中的索引（如A、B等）
    name VARCHAR(255),                 -- 题目的名称
    `type` VARCHAR(255),               -- 题目的类型
    points FLOAT,                      -- 题目的分值
    rating INT,                        -- 题目的难度等级
    tags VARCHAR(255),                 -- 题目的标签或分类
    PRIMARY KEY (contest_id, `index`) 
);

-- 比赛表，存储Codeforces比赛的相关信息
CREATE TABLE tb_contest (
    id INT PRIMARY KEY,                -- 比赛的唯一标识符
    name VARCHAR(255),                 -- 比赛的名称
    start_time_seconds BIGINT,         -- 比赛开始的时间戳（秒）
    duration_seconds BIGINT,           -- 比赛持续的总秒数
    relative_time_seconds BIGINT,      -- 当前距离比赛开始的相对时间（秒）
    phase VARCHAR(255),                -- 比赛的当前阶段（如：即将开始、进行中、结束）
    type VARCHAR(255),                 -- 比赛的类型（如：教育、官方）
    frozen BOOL                        -- 比赛结果是否被冻结
);

-- 题目统计数据，记录每道题目的解决情况
CREATE TABLE tb_problem_statistics (
    id INT PRIMARY KEY AUTO_INCREMENT, -- 统计记录的唯一标识符
    contest_id INT,                    -- 题目所属的比赛ID
    `index` VARCHAR(10),               -- 题目在比赛中的索引
    solved_count INT                   -- 解决该题目的用户数量
);

-- Codeforces用户表，存储用户在Codeforces平台上的详细信息
CREATE TABLE tb_cf_user (
    handle VARCHAR(255) NOT NULL PRIMARY KEY, -- 用户的Codeforces账号名
    email VARCHAR(255),                       -- 用户的电子邮件地址
    vkId VARCHAR(255),                        -- 用户的VK社交网络ID
    openId VARCHAR(255),                      -- 用户的OpenID
    first_name VARCHAR(255),                  -- 用户的名字
    last_name VARCHAR(255),                   -- 用户的姓氏
    country VARCHAR(255),                     -- 用户所在国家
    city VARCHAR(255),                        -- 用户所在城市
    organization VARCHAR(255),                -- 用户所属组织或学校
    contribution INT,                         -- 用户对社区的贡献值
    rank VARCHAR(255),                        -- 用户的当前排名称号
    rating INT,                               -- 用户的当前评级
    max_rank VARCHAR(255),                    -- 用户历史最高排名称号
    max_rating INT,                           -- 用户历史最高评级
    last_online_time_seconds BIGINT,          -- 用户最后在线时间的时间戳（秒）
    registration_time_seconds BIGINT,         -- 用户注册时间的时间戳（秒）
    friend_of_count INT,                      -- 用户的好友数量
    avatar VARCHAR(255),                      -- 用户的头像URL
    title_photo VARCHAR(255),                 -- 用户的标题图片URL
    holder VARCHAR(255),                      -- 用户的证书持有者信息
    account_type INT                          -- 用户账户类型
);

-- Codeforces用户评级变化表，记录用户评级的变化历史
CREATE TABLE tb_cf_user_rating (
    id INT AUTO_INCREMENT PRIMARY KEY,      -- 记录的唯一标识符
    contest_id INT,                         -- 相关比赛的ID
    contest_name VARCHAR(255),              -- 相关比赛的名称
    handle VARCHAR(255),                    -- 用户的Codeforces账号名
    rank INT,                               -- 用户在比赛中的排名
    rating_update_time_seconds BIGINT,      -- 评级更新时间的时间戳（秒）
    old_rating INT,                         -- 更新前的评级
    new_rating INT                          -- 更新后的评级
);

-- Codeforces提交记录表，记录用户的提交信息
CREATE TABLE tb_cf_submission (
    contest_id INT,                         -- 提交所在的比赛ID
    creation_time_seconds BIGINT,           -- 提交创建的时间戳（秒）
    problem_id INT,                         -- 提交的题目ID
    handle VARCHAR(255),                    -- 提交的用户账号名
    participant_type VARCHAR(255),          -- 用户参与比赛的身份类型
    programming_language VARCHAR(255),      -- 使用的编程语言
    verdict VARCHAR(255),                   -- 提交的判断结果
    time_consumed_millis BIGINT,            -- 执行代码消耗的时间（毫秒）
    passed_test_count INT,                  -- 通过的测试点数量
    PRIMARY KEY (contest_id, handle, problem_id, participant_type, creation_time_seconds)
);
```
