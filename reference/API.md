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
	- [1.3.1 多主机分开查询](#131-多主机分开查询)
	- [1.3.2 总量查询](#132-总量查询)
	- [1.3.3 查询指定服务器](#133-查询指定服务器)
	- [1.3.4 磁盘占用率查询](#134-磁盘占用率查询)
	- [1.3.5 内存使用情况查询](#135-内存使用情况查询)
- [1.4 数据增量模块](#14-数据增量模块)
	- [1.4.1 爬虫速率](#141-爬虫速率)
	- [1.4.2 IP池](#142-IP池)
	- [1.4.3 查询最近不为零的七天数据增量](#143-查询最近不为零的七天数据增量)
- [1.5 微博用户信息模块](#15-微博用户信息模块)
	
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

### 1.3.1 多主机分开查询

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

### 1.3.2 总量查询
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
    "code": 0,
    "message": "",
    "data": {
        "serverCount": 4,
        "size": 1339618747888,
        "hosts": [
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
            }
        ],
        "storageSize": 1355129837920,
        "count": 635319848,
        "avgSize": 2108,
        "okCount": 4,
        "totalIndexSize": 29275410752
    }
}
```

### 1.3.3 查询指定服务器
- GET /api/statistics/{socket}
- desc : 
    - 这里根据socket查询指定mongoDB数据库的信息，使用合法标识符“-”替换“.”和“:”
    - URL示例 : localhost:8081/api/statistics/222-27-227-110-27017
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
    "data": {
        "size": 333713266496,
        "storageSize": 337709246048,
        "count": 158267961,
        "avgSize": 2108,
        "host": {
            "host": "222.27.227.110",
            "port": 27017,
            "socketAddress": "222.27.227.110:27017"
        },
        "totalIndexSize": 7301666736,
        "status": true
    }
}
```

### 1.3.4 磁盘占用率查询
- GET /api/statistics/disk
- desc : 
    - 这里查询每个数据库服务器当前磁盘的使用情况
    - 磁盘占用情况变更和上述数据量变更一般是同步的
- return : 
    - host : 主机IP
    - used : 已用存储容量（单位字节）
    - all : 总存储容量（单位字节）
    - rate : 已用存储容量占总存储容量的比值
```json
{
    "code": 0,
    "message": "",
    "data": [
        {
            "all": 979942150144,
            "rate": 0.9986181537269307,
            "host": "222.27.227.104",
            "used": 978588020736
        },
        {
            "all": 979942150144,
            "rate": 0.7508403534105345,
            "host": "222.27.227.107",
            "used": 735780110336
        },
        {
            "all": 979942150144,
            "rate": 0.7268614554760523,
            "host": "222.27.227.110",
            "used": 712282177536
        },
        {
            "all": 979942150144,
            "rate": 0.7313348273943393,
            "host": "222.27.227.113",
            "used": 716665823232
        }
    ]
}
```

### 1.3.5 内存使用情况查询
- GET /api/statistics/mem
- desc : 
    - 这里查询每个数据库服务器当前内存的占用情况
    - 内存变化更加频繁
- return : 
    - host : 主机IP
    - used : 已用内存容量（单位字节）
    - all : 总内存大小（单位字节）
    - rate : 已用内存容量占总内存的比值
```json
{
    "code": 0,
    "message": "",
    "data": [
        {
            "all": 3810693120,
            "rate": 0.6700385340921916,
            "host": "222.27.227.104",
            "used": 2553311232
        },
        {
            "all": 3811061760,
            "rate": 0.21962415429342189,
            "host": "222.27.227.107",
            "used": 837001216
        },
        {
            "all": 3811061760,
            "rate": 0.445113307216517,
            "host": "222.27.227.110",
            "used": 1696354304
        },
        {
            "all": 3811069952,
            "rate": 0.7031652868490827,
            "host": "222.27.227.113",
            "used": 2679812096
        }
    ]
}
```

----

## 1.4 数据增量模块

### 1.4.1 爬虫速率

### 1.4.2 IP池
- GET /api/spider/ippool
- desc : 
    - 每一个IP都以套接字的形式进行返回
    - IP池基本五分钟一次更新，默认大小为8，但是可变
    - IP池在后端是对Redis进行访问得到的，所以离开了集群环境无法获取IP池
- return : 
    - status : IP池启用状态
    - lastRefresh : IP池的上次更新时间
    - refreshInterval : IP池的上一次更新距离大上次的更新的时间间隔
    - pool : IP池的内容，格式是socket
```json
{
    "code" : 0,
    "message" : "",
    "data" : {
        "status" : true,
        "lastRefresh" : "2018-4-9 18:00:01",
        "refreshInterval" : 5,
        "pool" : [
            "10.549.214.53:8910",
            "10.549.214.53:8910",
            "10.549.214.53:8910",
            "10.549.214.53:8910",
            "10.549.214.53:8910",
            "10.549.214.53:8910"
        ]
    }
}
```
    

### 1.4.3 查询最近不为零的七天数据增量
- GET /api/statistics/sevenday
- payload : 
    - show_empty : 
        - false : 选择离当前日期最近的且数据量增量非零的日期进行查询（推荐）
        - true : 查询近七天的数据增量，此时可能出现增量为零的情况
```json
{
    "show_empty" : false
}
```
- return :
    - date : 日期
    - count : 当天新增的记录数
    - disk : 当日新增的记录所占磁盘大小
    - rate : 上一个日期相比增幅或者降幅
```json
{
    "code" : 0,
    "message" : "",
    "data" : [
        {
            "date" : "2018-2-5",
            "count" : 546835145,
            "disk" : 46865154.1,
            "rate" : 0.12
        },
        {
            "date" : "2018-2-5",
            "count" : 546835145,
            "disk" : 46865154.1,
            "rate" : -0.23
        },
        {
            "date" : "2018-2-5",
            "count" : 546835145,
            "disk" : 46865154.0,
            "rate" : 0.24
        }
        // ...
    ]
}
```