# 1. weibo-API


<!-- TOC -->
- [1.1 Tips](#11-Tips)
- [1.2 用户模块](#12-用户模块)
	- [1.2.1 用户登录](#121-用户登录)
	- [1.2.2 用户注册](#122-用户注册)
	- [1.2.3 用户名判重](#123-用户名判重)
	- [1.2.4 修改密码](#124-修改密码)
	- [1.2.5 用户注销](#125-用户注销)
- [1.3 数据量统计模块](#13-数据量统计模块)
	- [1.3.1 mongoDB数据状态查询-多主机](#131-mongoDB数据状态查询-多主机)
	- [1.3.2 mongoDB数据状态查询-合计](#132-mongoDB数据状态查询-合计)
- [1.4 爬虫状态模块](#14-爬虫模块)
	- [1.4.1 爬虫速率](#141-爬虫速率)
	- [1.4.2 IP池](#142-IP池)
    
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
    
----

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

## 1.3 数据量统计模块

### 1.3.1 mongoDB数据状态查询-多主机

- GET /api/statistics/splited
- desc :
    - 这里统计的是每台mongoDB数据库服务器的数据量
- return :
    - host : 主机信息
    - count：微博记录数
    - size：微博记录所使用的内存大小（单位：字节）
    - avgSize：每条微博的平均内存大小（单位：字节）
    - storageSize：当前分配的内存大小（单位：字节）
    - totalIndexSize：索引大小（单位：字节）
    - status：数据库状态（true：正常状态）
```json
{
    "code": 0,
    "message": "",
    "data": [
        {
            "size": 333713266496,
            "storageSize": 337709246048,
            "count": 158267961,
            "avgSize": 2108,
            "host": {
                "host": "222.27.227.104",
                "port": 27017,
                "socketAddress": "222.27.227.104:27017"
            },
            "totalIndexSize": 7301666736,
            "status": true
        },
        {
            "size": 334250032080,
            "storageSize": 337709246048,
            "count": 158519400,
            "avgSize": 2108,
            "host": {
                "host": "222.27.227.107",
                "port": 27017,
                "socketAddress": "222.27.227.107:27017"
            },
            "totalIndexSize": 7289697072,
            "status": true
        },
        {
            "size": 335223868352,
            "storageSize": 339855672912,
            "count": 158975696,
            "avgSize": 2108,
            "host": {
                "host": "222.27.227.110",
                "port": 27017,
                "socketAddress": "222.27.227.110:27017"
            },
            "totalIndexSize": 7322597296,
            "status": true
        },
        {
            "size": 336431580960,
            "storageSize": 339855672912,
            "count": 159556791,
            "avgSize": 2108,
            "host": {
                "host": "222.27.227.113",
                "port": 27017,
                "socketAddress": "222.27.227.113:27017"
            },
            "totalIndexSize": 7361449648,
            "status": true
        }
    ]
}
```

### 1.3.2 mongoDB数据状态查询-合计
- GET /api/statistics/total
- desc : 
    - 这里统计的是多台服务器的总的数据量
    - 提供这个接口是为了方便前端的编写，提高灵活性
- return :
    - serverCount : 数据库服务器的数目
    - hosts : 各个数据库服务器的host信息
    - count : 微博记录数
    - size : 微博记录所使用的总内存大小（单位：字节）
    - avgSize : 每条微博的平均内存大小（单位：字节）
    - storageSize : 当前分配的总内存大小（单位：字节）
    - totalIndexSize : 索引总大小（单位：字节）
    - okCount : 数据库状态正常的数据库服务器数目
```json
{
  "code" : 0,
  "message" : "",
  "data" : {
      "serverCount" : 4,
      "hosts" : [
        {
            "host": "222.27.227.104",
            "port": 27017,
            "socketAddress": "222.27.227.104:27017"
        },
        {
            "host": "222.27.227.107",
            "port": 27017,
            "socketAddress": "222.27.227.107:27017"
        },
        {
            "host": "222.27.227.110",
            "port": 27017,
            "socketAddress": "222.27.227.110:27017"
        },
        {
            "host": "222.27.227.113",
            "port": 27017,
            "socketAddress": "222.27.227.113:27017"
        },
      ],
      "count" : 158267961,
      "size" : 333713266496.0,
      "avgSize" : 2108,
      "storageSize" :  337709246048.0,
      "totalIndexSize" : 7301666736.0,
      "okCount" : 4
    }
}
```
