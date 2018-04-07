# 1. weibo-API


<!-- TOC -->
- [1.1 Tips](#11-Tips)
- [1.2 用户模块](#12-用户模块)
	- [1.2.1 用户登录](#121-用户登录)
	- [1.2.2 用户注册](#122-用户注册)
	- [1.2.3 用户名判重](#123-用户名判重)
	- [1.2.4 修改密码](#124-修改密码)
	- [1.2.5 用户注销](#125-用户注销)
- [1.3 微博统计信息模块](#13-微博统计信息模块)
	- [1.3.1 统计信息查询](#131-统计信息查询)
- [1.4 微博用户信息模块](#14-微博用户信息模块)
	
<!-- /TOC -->


## 1.1 tips

- 单用户登录
- code :
  - 0 : 一切正常
  - 1 : 直接将message展示给用户
- 接口在学校服务器上已经部署，前端开发时用校内网直接连即可
    - 主机IP：10.90.6.251
    - 端口号：8081
---

## 1.2. 用户模块

### 1.2.1 用户登录

- POST /api/user/login
- desc : 
    - 用户每次登录都会更新token，但是token不会过期
    - 新旧密码必须不同且不能为空
- payload :
```json
{
  "username": "administrator",
  "passwd": "adminOfSystem"
}
```
- return :
    - token：登陆令牌
    - username：成功登陆后的用户名
    - lastLogin：上次登陆时间的时间戳
```json
{
  "code" : 0,
  "message" : "",
  "data" : {
    "token" : "SDF23SFW84ASF65S",
    "username" : "administrator",
    "lastLogin" : "2015-5-3 18:00:00"
  }
}
```

### 1.2.2 用户注册
- POST /api/user/register
- desc : 
    - 注册成功之后在后台不会生成token
- payload : 
```json
{
  "username": "administrator",
  "passwd": "adminOfSystem"
}
```
- return : 
```json
{
  "code" : 0,
  "message" : "",
  "data" : {}
}
```

### 1.2.3 用户名判重
- GET /api/user/register
- desc : 
    - data中只包含true/false两种值
    - true代表用户名已存在，false代表用户名可用
- payload : 
```json
{
  "username" : "administrator"
}
```
- return :
```json
{
  "code" : 0,
  "message" : "",
  "data" : false
}
```

### 1.2.4 修改密码

- PUT /api/user/passwd
- desc：
    - 要求用户登陆之后才能修改密码
- payload :
```json
{
    "oldpasswd": "adminOfSystem",
    "newpasswd": "newpasswdOfAdmin"
}
```
- return :
```json
{
  "code": 0,
  "message" : "",
  "data": {}
}
```

### 1.2.5 用户注销

- DELETE /api/user/login
- desc：
    - 注销会清空数据库中持久化的用户的token
- return :
```json
{
  "code" : 0,
  "message" : "",
  "data" : {}
}
```

----

## 1.3 微博统计信息模块

### 1.3.1 统计信息查询

- GET /api/statistics
- return :
    - count：微博记录数
    - size：微博记录所使用的内存大小（单位：字节）
    - avgSize：每条微博的平均内存大小（单位：字节）
    - storageSize：当前分配的内存大小（单位：字节）
    - totalIndexSize：索引大小（单位：字节）
    - status：数据库状态（true：正常状态）
```json
{
  "code" : 0,
  "message" : "",
  "data" : [
    {
      "count" : 158267961,
      "size" : 333713266496.0,
      "avgSize" : 2108,
      "storageSize" :  337709246048.0,
      "totalIndexSize" : 7301666736.0,
      "status" : true
    },
    {
      "count" : 158267961,
      "size" : 333713266496.0,
      "avgSize" : 2108,
      "storageSize" :  337709246048.0,
      "totalIndexSize" : 7301666736.0,
      "status" : true
    },
    {
      "count" : 158267961,
      "size" : 333713266496.0,
      "avgSize" : 2108,
      "storageSize" :  337709246048.0,
      "totalIndexSize" : 7301666736.0,
      "status" : true
    },
   {
      "count" : 158267961,
      "size" : 333713266496.0,
      "avgSize" : 2108,
      "storageSize" :  337709246048.0,
      "totalIndexSize" : 7301666736.0,
      "status" : true
    }
  ]
}
```


----

## 1.4
```json
{
    "startTime":"2010/11/11",
    "endTime":"2010/12/12"
}
```
- return :

```json
{
    "code": 0,
    "data": [
        {
            "systemId": 1,
            "userName":"小明",
            "sex":"男",
            "likes":50,
            "repost":200,
            "comments":23,
            "content":"hello world",
            "publishTime":"2017/10/11"
        }
    ]
}
```

---

## 1.4. 微博统计

### 1.4.1. 最近每日微博条数

- GET /weibo/sum?

    -day:该天日期


- return :
  - data : 从day往前一周 每日的微博数量 数组

```json
{
    "code": 0,
    "data": [20, 30, 50, 23, 68, 78, 90]
}
```

---

### 1.4.2. 某日/今日微博转发量最多前五

- GET /weibo/repost?
  - today : 该天的日期

- return:
  - data：对象数组 按转发量由大到小 五个微博对象


```json
{
    "code": 0,
    "data": [
        {
            "systemId": 1,
            "userName": "小张",
            "sex":"男",
            "content": "hhhhhhhhello",
            "comments":4329,
            "repost": 5000,
            "likes":456
        }
    ]
}
```

---

### 1.4.3. 某日/今日微博点赞数最多前五

- GET /weibo/likes?
    - today:该天日期 

- return：
    - data:点赞数最多前五的微博对象

```json
{
    "code": 0,
    "data": [
        {
            "systemId": 1,
            "userName": "小张",
            "sex":"男",
            "content": "hhhhhhhhello",
            "comments":4329,
            "repost": 5000,
            "likes":40000
        }
    ]
}
```
---

### 1.4.4. 某日/今日微博评论数最多前五

- GET /weibo/comments?
    - today:该天日期 

- return：
    - data:评论数最多前五的微博对象

```json
{
    "code": 0,
    "data": [
        {
            "systemId": 1,
            "userName": "小张",
            "sex":"男",
            "content": "hhhhhhhhello",
            "comments":4329,
            "repost": 5000,
            "likes":40000
        }
    ]
}
```
---

## 1.5. 大数据统计

### 1.5.1. 库中近年每年的微博数量

- GET /weibo/yearsum？

    -year:该年年份


- return :
  - data : 从year往前七年 每年的微博数量 数组

```json
{
    "code": 0,
    "data": [200000, 300000, 500000, 230000, 680000, 780000, 900000]
}
```

---

### 1.5.2.某年/今年微博转发量最多前十

- GET /weibo/yearRepost?
  - year : 该年年份

- return:
  - data：对象数组 按转发量由大到小 十个微博对象


```json
{
    "code": 0,
    "data": [
        {
            "systemId": 1,
            "userName": "小张",
            "sex":"男",
            "content": "hhhhhhhhello",
            "comments":4329,
            "repost": 5000,
            "likes":456
        }
    ]
}
```

---

### 1.5.3. 某年/今年微博点赞数最多前十

- GET /weibo/yearLikes?
    - year:该年年份 

- return：
    - data:点赞数最多前十的微博对象

```json
{
    "code": 0,
    "data": [
        {
            "systemId": 1,
            "userName": "小张",
            "sex":"男",
            "content": "hhhhhhhhello",
            "comments":4329,
            "repost": 5000,
            "likes":40000
        }
    ]
}
```

---

### 1.5.4. 某年/今年微博评论数最多前十

- GET /weibo/yearComments?
    - year:该年年份 

- return：
    - data:评论数最多前十的微博对象

```json
{
    "code": 0,
    "data": [
        {
            "systemId": 1,
            "userName": "小张",
            "sex":"男",
            "content": "hhhhhhhhello",
            "comments":4329,
            "repost": 5000,
            "likes":40000
        }
    ]
}
```
---

### 1.5.5. 某年（今）各省份微博用户比重

- GET /weibo/yearUsers?
    - year:该年年份 

- return：
    - data:各省微博用户数量百分比

```json
{
    "code": 0,
    "data": 
        {
            "henan": "10%",
            "hubei": "40%",
            "heilongjiang":"25%"
        }
}
```

---
## 1.6 后端相关

### 1.6.1 四集群相关数据

- GET /weibo/system

- return：
    
    -data:集群各项指标，待定
```json
{
    "code": 0,
    "data": [
        {
            
        }
    ]
}
```