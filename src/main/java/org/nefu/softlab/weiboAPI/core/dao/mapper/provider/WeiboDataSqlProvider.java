package org.nefu.softlab.weiboAPI.core.dao.mapper.provider;

import org.apache.ibatis.jdbc.SQL;
import org.nefu.softlab.weiboAPI.core.po.WeiboData;

import java.util.List;
import java.util.Map;

/**
 * Created by Jiaxu_Zou on 2018-4-7
 */
public class WeiboDataSqlProvider {

    // written

    /**
     * 根据是否存在uids构造出两种sql
     * @param condition
     * @return
     */
    public String selectRecords(Map<String, Object> condition) {
        if (condition.get("uid") != null && ((String)condition.get("uid")).trim().equals("") == false)
            return "SELECT * FROM `t_weibo_data` WHERE `uid`=#{uid} AND `time` >= #{from} AND `time` <= #{to}";
        else
            return "SELECT * FROM `t_weibo_data` WHERE `time`>=#{from} AND `time`<=#{to}";
    }

    // generated

    public String insertSelective(WeiboData record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_weibo_data");
        
        if (record.getWid() != null) {
            sql.VALUES("wid", "#{wid,jdbcType=VARCHAR}");
        }
        
        if (record.getAttu() != null) {
            sql.VALUES("attu", "#{attu,jdbcType=INTEGER}");
        }
        
        if (record.getComment() != null) {
            sql.VALUES("comment", "#{comment,jdbcType=INTEGER}");
        }
        
        if (record.getDevice() != null) {
            sql.VALUES("device", "#{device,jdbcType=VARCHAR}");
        }
        
        if (record.getTime() != null) {
            sql.VALUES("time", "#{time,jdbcType=BIGINT}");
        }
        
        if (record.getRewid() != null) {
            sql.VALUES("rewid", "#{rewid,jdbcType=VARCHAR}");
        }
        
        if (record.getUid() != null) {
            sql.VALUES("uid", "#{uid,jdbcType=VARCHAR}");
        }
        
        if (record.getContent() != null) {
            sql.VALUES("content", "#{content,jdbcType=LONGVARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(WeiboData record) {
        SQL sql = new SQL();
        sql.UPDATE("t_weibo_data");
        
        if (record.getAttu() != null) {
            sql.SET("attu = #{attu,jdbcType=INTEGER}");
        }
        
        if (record.getComment() != null) {
            sql.SET("comment = #{comment,jdbcType=INTEGER}");
        }
        
        if (record.getDevice() != null) {
            sql.SET("device = #{device,jdbcType=VARCHAR}");
        }
        
        if (record.getTime() != null) {
            sql.SET("time = #{time,jdbcType=BIGINT}");
        }
        
        if (record.getRewid() != null) {
            sql.SET("rewid = #{rewid,jdbcType=VARCHAR}");
        }
        
        if (record.getUid() != null) {
            sql.SET("uid = #{uid,jdbcType=VARCHAR}");
        }
        
        if (record.getContent() != null) {
            sql.SET("content = #{content,jdbcType=LONGVARCHAR}");
        }
        
        sql.WHERE("wid = #{wid,jdbcType=VARCHAR}");
        
        return sql.toString();
    }
}