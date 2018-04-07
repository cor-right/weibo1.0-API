package org.nefu.softlab.weiboAPI.core.DAO.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.nefu.softlab.weiboAPI.core.DAO.mapper.provider.LogSqlProvider;
import org.nefu.softlab.weiboAPI.core.PO.Log;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LogMapper {
    @Delete({
        "delete from t_user_log",
        "where logid = #{logid,jdbcType=CHAR}"
    })
    int deleteByPrimaryKey(String logid);

    @Insert({
        "insert into t_user_log (logid, count, ",
        "ip, timeStamp, lastLogin)",
        "values (#{logid,jdbcType=CHAR}, #{count,jdbcType=INTEGER}, ",
        "#{ip,jdbcType=CHAR}, #{timestamp,jdbcType=VARCHAR}, #{lastlogin,jdbcType=CHAR})"
    })
    int insert(Log record);

    @InsertProvider(type=LogSqlProvider.class, method="insertSelective")
    int insertSelective(Log record);

    @Select({
        "select",
        "logid, count, ip, timeStamp, lastLogin",
        "from t_user_log",
        "where logid = #{logid,jdbcType=CHAR}"
    })
    @Results({
        @Result(column="logid", property="logid", jdbcType=JdbcType.CHAR, id=true),
        @Result(column="count", property="count", jdbcType=JdbcType.INTEGER),
        @Result(column="ip", property="ip", jdbcType=JdbcType.CHAR),
        @Result(column="timeStamp", property="timestamp", jdbcType=JdbcType.VARCHAR),
        @Result(column="lastLogin", property="lastlogin", jdbcType=JdbcType.CHAR)
    })
    Log selectByPrimaryKey(String logid);

    @UpdateProvider(type=LogSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Log record);

    @Update({
        "update t_user_log",
        "set count = #{count,jdbcType=INTEGER},",
          "ip = #{ip,jdbcType=CHAR},",
          "timeStamp = #{timestamp,jdbcType=VARCHAR},",
          "lastLogin = #{lastlogin,jdbcType=CHAR}",
        "where logid = #{logid,jdbcType=CHAR}"
    })
    int updateByPrimaryKey(Log record);
}