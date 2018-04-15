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
- [1.4 数据增量模块](#14-数据增量模块)
	- [1.4.1 爬虫速率](#141-爬虫速率)
	- [1.4.2 IP池](#142-IP池)
	- [1.4.3 查询最近不为零的七天数据增量](#143-查询最近不为零的七天数据增量)
	- [1.4.4 内存使用情况查询](#144-内存使用情况查询)
	- [1.4.5 当日数据增量查询](#145-当日数据增量查询)
- [1.5  数据检索模块](#15-数据检索模块)
	- [1.5.1 微博大咖用户查询](#151-微博大咖用户查询)
	- [1.5.2 查询默认数据量](#152-查询默认数据量)
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
    - diskAll : 磁盘总容量
    - diskLeft : 磁盘剩余可用容量
```json
{
    "code": 0,
    "message": "",
    "data": [
        {
            "size": 333713266496,
            "diskAll": 979942150144,
            "storageSize": 337709246048,
            "count": 158267961,
            "avgSize": 2108,
            "host": {
                "host": "222.27.227.104",
                "port": 27017,
                "socketAddress": "222.27.227.104:27017"
            },
            "diskLeft": 1429630976,
            "totalIndexSize": 7301666736,
            "status": true
        },
        {
            "size": 334250032080,
            "diskAll": 979942150144,
            "storageSize": 337709246048,
            "count": 158519400,
            "avgSize": 2108,
            "host": {
                "host": "222.27.227.107",
                "port": 27017,
                "socketAddress": "222.27.227.107:27017"
            },
            "diskLeft": 244161863680,
            "totalIndexSize": 7289697072,
            "status": true
        },
        {
            "size": 335223868352,
            "diskAll": 979942150144,
            "storageSize": 339855672912,
            "count": 158975696,
            "avgSize": 2108,
            "host": {
                "host": "222.27.227.110",
                "port": 27017,
                "socketAddress": "222.27.227.110:27017"
            },
            "diskLeft": 267250081792,
            "totalIndexSize": 7322597296,
            "status": true
        },
        {
            "size": 336431580960,
            "diskAll": 979942150144,
            "storageSize": 339855672912,
            "count": 159556791,
            "avgSize": 2108,
            "host": {
                "host": "222.27.227.113",
                "port": 27017,
                "socketAddress": "222.27.227.113:27017"
            },
            "diskLeft": 263233064960,
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



----

## 1.4 数据增量模块

### 1.4.1 爬虫速率
- GET /api/spider/status
- desc :
    - 获取爬虫速率
- return :
    - status : 爬虫启用状态，true为启用中
    - nodeC ount : 爬虫节点数，默认12
    - curRate : 当前爬虫速率，单位(条/秒)
    - curAvgRate : 当前每个节点上爬虫的平均速率，单位(条/秒)
    - curRate : 五分钟内爬虫的平均速率，单位(条/秒)
```json
{
    "code" : 0,
    "message" : "",
    "data" : {
        "status" : true,
        "nodeCount" : 12,
        "curRate" : 348.45,
        "curAvgRate" : 24.34,
        "rateInFive" : 367.54
    }
}
```

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
    "code": 0,
    "message": "",
    "data": {
        "lastRefresh": "2018-04-10 13:08:43",
        "refreshInterval": 6,
        "pool": [
            "116.17.236.180:56792",
            "121.205.254.12:38926",
            "114.101.132.38:14826",
            "125.116.98.114:10656",
            "114.99.27.42:11187",
            "117.69.96.59:25386",
            "111.226.188.74:32221",
            "171.80.186.185:13166"
        ],
        "status": true
    }
}
```
    

### 1.4.3 查询最近不为零的七天数据增量
- GET /api/statistics/sevenday
- desc : 
    - 会读取数据库中有记录的离当前日期最近的七天
- return :
    - date : 日期
    - count : 当天新增的记录数
    - disk : 当日新增的记录所占磁盘大小
```json
{
    "code": 0,
    "message": "",
    "data": [
        {
            "date": "2018-04-03",
            "disk": 548654857845,
            "count": 546868768
        },
        {
            "date": "2018-04-04",
            "disk": 158565486548,
            "count": 613548548
        },
        {
            "date": "2018-04-05",
            "disk": 434567894562,
            "count": 614856678
        },
        {
            "date": "2018-04-06",
            "disk": 185412545956,
            "count": 123485486
        },
        {
            "date": "2018-04-07",
            "disk": 113254854695,
            "count": 486548657
        },
        {
            "date": "2018-04-08",
            "disk": 135485648564,
            "count": 618486544
        },
        {
            "date": "2018-04-09",
            "disk": 145846578541,
            "count": 625465351
        }
    ]
}
```

### 1.4.4 内存使用情况查询
- GET /api/spider/mem
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

### 1.4.5 当日数据增量查询
- GET /api/spider/increase/today
- return :
    - grow_count : 今日当前相对于昨天的数据条数的增量
    - grow_disk : 今日相对于昨天的数据磁盘占有量的增量
    - host : 主机IP
```json
{
    "code": 0,
    "message": "",
    "data": [
        {
            "grow_count": 22607,
            "grow_disk": -207106,
            "host": "222.27.227.104"
        },
        {
            "grow_count": 6983944,
            "grow_disk": 4691403,
            "host": "222.27.227.107"
        },
        {
            "grow_count": 45427042,
            "grow_disk": 24387207500,
            "host": "222.27.227.110"
        },
        {
            "grow_count": 0,
            "grow_disk": 0,
            "host": "222.27.227.113"
        }
    ]
}
```

## 1.5 数据检索模块

### 1.5.1 微博大咖用户查询
- GET /api/content/user/famous
- desc : 
    - 返回指定数目的微博大咖用户用于前台滚动展示
    - 因为用户信息是手动导入的，所以不需要做实时监控
    - 默认数目为：50
    - 默认排序规则为：按粉丝数从多到少排列
- return :
    - headurl : 头像连接，可以直接放在img的src下
    - weibonum : 已发的微博数
    - fansnum : 粉丝数
    - connum : 关注数（该用户关注其他用户的数目）
```json
{
    "code": 0,
    "message": "",
    "data": [
        {
            "uid": "1934183965",
            "nickname": "微博管理员",
            "gender": "女",
            "birthday": "",
            "headurl": "http://tva1.sinaimg.cn/crop.0.0.180.180.180/73494e1djw1e8qgp5bmzyj2050050aa8.jpg",
            "place": "北京",
            "weibonum": 1243,
            "connum": 171,
            "fansnum": 159142321,
            "marriage": "单身",
            "signature": "大家好，我的职责是和大家一起维护微博社区秩序。针对微博中的不实信息、抄袭、人身攻击等行为请直接举报，我们会第一时间处理"
        },
        {
            "uid": "1938284521",
            "nickname": "李敏镐",
            "gender": "男",
            "birthday": "01-01",
            "headurl": "http://tva2.sinaimg.cn/crop.0.0.180.180.180/7387dfe9jw1e8qgp5bmzyj2050050aa8.jpg",
            "place": "海外 韩国",
            "weibonum": 327,
            "connum": 9,
            "fansnum": 28651104,
            "marriage": "单身",
            "signature": "韩国演员李敏镐，工作联系：mym@mym-ent.com"
        },
        // ...
    ]
}
```

### 1.5.2 查询默认数据量
- GET /api/content/number
- payload :
    - user : 是否获取用户数据量
    - weibo: 是否获取微博数据量 
```json
{
    "code": 0,
    "message": "",
    "data": {
        "weibo": 635741152,
        "user": 892764
    }
}
```
- desc : 
    - 这里就是简单的对数据量的查询
- return :
```json
{
    "code" : 0,
    "message" : 1
    "data" : {
        "user" : ,
        "weibo" : 
    }
}
```


    

