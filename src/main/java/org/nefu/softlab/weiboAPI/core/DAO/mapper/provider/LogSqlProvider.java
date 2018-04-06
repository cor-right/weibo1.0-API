package org.nefu.softlab.weiboAPI.core.DAO.mapper.provider;

import org.apache.ibatis.jdbc.SQL;
import org.nefu.softlab.weiboAPI.core.PO.Log;

public class LogSqlProvider {

    public String insertSelective(Log record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_user_log");
        
        if (record.getLogid() != null) {
            sql.VALUES("logid", "#{logid,jdbcType=CHAR}");
        }
        
        if (record.getCount() != null) {
            sql.VALUES("count", "#{count,jdbcType=INTEGER}");
        }
        
        if (record.getIp() != null) {
            sql.VALUES("ip", "#{ip,jdbcType=CHAR}");
        }
        
        if (record.getTimestamp() != null) {
            sql.VALUES("timeStamp", "#{timestamp,jdbcType=VARCHAR}");
        }
        
        if (record.getLastlogin() != null) {
            sql.VALUES("lastLogin", "#{lastlogin,jdbcType=CHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Log record) {
        SQL sql = new SQL();
        sql.UPDATE("t_user_log");
        
        if (record.getCount() != null) {
            sql.SET("count = #{count,jdbcType=INTEGER}");
        }
        
        if (record.getIp() != null) {
            sql.SET("ip = #{ip,jdbcType=CHAR}");
        }
        
        if (record.getTimestamp() != null) {
            sql.SET("timeStamp = #{timestamp,jdbcType=VARCHAR}");
        }
        
        if (record.getLastlogin() != null) {
            sql.SET("lastLogin = #{lastlogin,jdbcType=CHAR}");
        }
        
        sql.WHERE("logid = #{logid,jdbcType=CHAR}");
        
        return sql.toString();
    }
}