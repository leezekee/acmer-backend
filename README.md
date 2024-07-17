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
