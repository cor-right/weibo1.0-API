# 用户表
# `t_user`
CREATE TABLE `t_user` (
  `uid` char(32) NOT NULL,
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `passwd` char(32) NOT NULL,
  `token` char(32) DEFAULT NULL,
  `lastLogin` char(32) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# 用户登陆日志表
# `t_user_log`
CREATE TABLE `t_user_log` (
  `logid` char(32) NOT NULL,
  `count` int(11) DEFAULT '0',
  `ip` char(16) DEFAULT NULL,
  `timeStamp` varchar(64) NOT NULL,
  `lastLogin` char(32) DEFAULT NULL,
  PRIMARY KEY (`logid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 微博用户信息表
# `t_weibo_user`
CREATE TABLE `t_user_info` (
  `uid` char(16) NOT NULL,
  `nickName` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `gender` char(8) DEFAULT NULL,
  `birthday` char(16) DEFAULT NULL,
  `headUrl` varchar(256) DEFAULT NULL,
  `place` varchar(256) DEFAULT NULL,
  `weiboNum` int(16) DEFAULT NULL,
  `conNum` int(16) DEFAULT NULL,
  `fansNum` int(16) DEFAULT NULL,
  `marriage` char(16) DEFAULT NULL,
  `signature` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#