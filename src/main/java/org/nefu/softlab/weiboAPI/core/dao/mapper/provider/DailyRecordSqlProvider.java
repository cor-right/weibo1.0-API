package org.nefu.softlab.weiboAPI.core.dao.mapper.provider;

import org.nefu.softlab.weiboAPI.core.po.DailyRecord;
import org.apache.ibatis.jdbc.SQL;

public class DailyRecordSqlProvider {

    public String insertSelective(DailyRecord record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_monitor_record_daily");
        
        if (record.getRid() != null) {
            sql.VALUES("rid", "#{rid,jdbcType=CHAR}");
        }
        
        if (record.getSocket() != null) {
            sql.VALUES("socket", "#{socket,jdbcType=VARCHAR}");
        }
        
        if (record.getRecordnumber() != null) {
            sql.VALUES("recordnumber", "#{recordnumber,jdbcType=INTEGER}");
        }
        
        if (record.getRecordsize() != null) {
            sql.VALUES("recordsize", "#{recordsize,jdbcType=DOUBLE}");
        }
        
        if (record.getDate() != null) {
            sql.VALUES("date", "#{date,jdbcType=CHAR}");
        }
        
        if (record.getSavetimestamp() != null) {
            sql.VALUES("saveTimestamp", "#{savetimestamp,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(DailyRecord record) {
        SQL sql = new SQL();
        sql.UPDATE("t_monitor_record_daily");
        
        if (record.getSocket() != null) {
            sql.SET("socket = #{socket,jdbcType=VARCHAR}");
        }
        
        if (record.getRecordnumber() != null) {
            sql.SET("recordnumber = #{recordnumber,jdbcType=INTEGER}");
        }
        
        if (record.getRecordsize() != null) {
            sql.SET("recordsize = #{recordsize,jdbcType=DOUBLE}");
        }
        
        if (record.getDate() != null) {
            sql.SET("date = #{date,jdbcType=CHAR}");
        }
        
        if (record.getSavetimestamp() != null) {
            sql.SET("saveTimestamp = #{savetimestamp,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("rid = #{rid,jdbcType=CHAR}");
        
        return sql.toString();
    }
}