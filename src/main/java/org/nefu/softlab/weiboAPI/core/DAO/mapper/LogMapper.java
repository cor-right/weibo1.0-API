package org.nefu.softlab.weiboAPI.core.DAO.mapper;

import org.apache.ibatis.annotations.*;
import org.nefu.softlab.weiboAPI.core.DAO.mapper.provider.LogSqlProvider;
import org.apache.ibatis.type.JdbcType;
import org.nefu.softlab.weiboAPI.core.PO.Log;
import org.springframework.stereotype.Repository;

/**
 * Created by Jiaxu_Zou on 2018-4-7
 */
@Mapper
@Repository
public interface LogMapper {

    @Delete({
        "delete from t_user_log",
        "where logid = #{logid,jdbcType=CHAR}"
    })
    int deleteByPrimaryKey(String logid);

    @Insert({
        "insert into t_user_log (logid, uid, count, ",
        "ip, useragent, hostname, ",
        "timeStamp, lastLogin)",
        "values (#{logid,jdbcType=CHAR}, #{uid,jdbcType=CHAR}, #{count,jdbcType=INTEGER}, ",
        "#{ip,jdbcType=CHAR}, #{useragent,jdbcType=VARCHAR}, #{hostname,jdbcType=VARCHAR}, ",
        "#{timestamp,jdbcType=VARCHAR}, #{lastlogin,jdbcType=CHAR})"
    })
    int insert(Log record);

    @InsertProvider(type=LogSqlProvider.class, method="insertSelective")
    int insertSelective(Log record);

    @Select({
        "select",
        "logid, uid, count, ip, useragent, hostname, timeStamp, lastLogin",
        "from t_user_log",
        "where logid = #{logid,jdbcType=CHAR}"
    })
    @Results({
        @Result(column="logid", property="logid", jdbcType=JdbcType.CHAR, id=true),
        @Result(column="uid", property="uid", jdbcType=JdbcType.CHAR),
        @Result(column="count", property="count", jdbcType=JdbcType.INTEGER),
        @Result(column="ip", property="ip", jdbcType=JdbcType.CHAR),
        @Result(column="useragent", property="useragent", jdbcType=JdbcType.VARCHAR),
        @Result(column="hostname", property="hostname", jdbcType=JdbcType.VARCHAR),
        @Result(column="timeStamp", property="timestamp", jdbcType=JdbcType.VARCHAR),
        @Result(column="lastLogin", property="lastlogin", jdbcType=JdbcType.CHAR)
    })
    Log selectByPrimaryKey(String logid);

    @UpdateProvider(type=LogSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Log record);

    @Update({
        "update t_user_log",
        "set uid = #{uid,jdbcType=CHAR},",
          "count = #{count,jdbcType=INTEGER},",
          "ip = #{ip,jdbcType=CHAR},",
          "useragent = #{useragent,jdbcType=VARCHAR},",
          "hostname = #{hostname,jdbcType=VARCHAR},",
          "timeStamp = #{timestamp,jdbcType=VARCHAR},",
          "lastLogin = #{lastlogin,jdbcType=CHAR}",
        "where logid = #{logid,jdbcType=CHAR}"
    })
    int updateByPrimaryKey(Log record);
}