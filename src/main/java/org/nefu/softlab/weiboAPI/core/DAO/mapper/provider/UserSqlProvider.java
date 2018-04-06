package org.nefu.softlab.weiboAPI.core.DAO.mapper.provider;

import org.apache.ibatis.jdbc.SQL;
import org.nefu.softlab.weiboAPI.core.PO.User;

public class UserSqlProvider {

    public String insertSelective(User record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_user");
        
        if (record.getUid() != null) {
            sql.VALUES("uid", "#{uid,jdbcType=CHAR}");
        }
        
        if (record.getUsername() != null) {
            sql.VALUES("username", "#{username,jdbcType=VARCHAR}");
        }
        
        if (record.getPasswd() != null) {
            sql.VALUES("passwd", "#{passwd,jdbcType=CHAR}");
        }
        
        if (record.getToken() != null) {
            sql.VALUES("token", "#{token,jdbcType=CHAR}");
        }
        
        if (record.getLastlogin() != null) {
            sql.VALUES("lastLogin", "#{lastlogin,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(User record) {
        SQL sql = new SQL();
        sql.UPDATE("t_user");
        
        if (record.getUsername() != null) {
            sql.SET("username = #{username,jdbcType=VARCHAR}");
        }
        
        if (record.getPasswd() != null) {
            sql.SET("passwd = #{passwd,jdbcType=CHAR}");
        }
        
        if (record.getToken() != null) {
            sql.SET("token = #{token,jdbcType=CHAR}");
        }
        
        if (record.getLastlogin() != null) {
            sql.SET("lastLogin = #{lastlogin,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("uid = #{uid,jdbcType=CHAR}");
        
        return sql.toString();
    }
}