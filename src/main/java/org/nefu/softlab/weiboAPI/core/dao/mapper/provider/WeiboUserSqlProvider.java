package org.nefu.softlab.weiboAPI.core.dao.mapper.provider;

import org.apache.ibatis.jdbc.SQL;
import org.nefu.softlab.weiboAPI.core.po.WeiboUser;

/**
 * Created by Jiaxu_Zou on 2018-4-7
 */
public class WeiboUserSqlProvider {

    public String insertSelective(WeiboUser record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_weibo_user");
        
        if (record.getUid() != null) {
            sql.VALUES("uid", "#{uid,jdbcType=CHAR}");
        }
        
        if (record.getNickname() != null) {
            sql.VALUES("nickName", "#{nickname,jdbcType=VARCHAR}");
        }
        
        if (record.getGender() != null) {
            sql.VALUES("gender", "#{gender,jdbcType=CHAR}");
        }
        
        if (record.getBirthday() != null) {
            sql.VALUES("birthday", "#{birthday,jdbcType=CHAR}");
        }
        
        if (record.getHeadurl() != null) {
            sql.VALUES("headUrl", "#{headurl,jdbcType=VARCHAR}");
        }
        
        if (record.getPlace() != null) {
            sql.VALUES("place", "#{place,jdbcType=VARCHAR}");
        }
        
        if (record.getWeibonum() != null) {
            sql.VALUES("weiboNum", "#{weibonum,jdbcType=INTEGER}");
        }
        
        if (record.getConnum() != null) {
            sql.VALUES("conNum", "#{connum,jdbcType=INTEGER}");
        }
        
        if (record.getFansnum() != null) {
            sql.VALUES("fansNum", "#{fansnum,jdbcType=INTEGER}");
        }
        
        if (record.getMarriage() != null) {
            sql.VALUES("marriage", "#{marriage,jdbcType=CHAR}");
        }
        
        if (record.getSignature() != null) {
            sql.VALUES("signature", "#{signature,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(WeiboUser record) {
        SQL sql = new SQL();
        sql.UPDATE("t_weibo_user");
        
        if (record.getNickname() != null) {
            sql.SET("nickName = #{nickname,jdbcType=VARCHAR}");
        }
        
        if (record.getGender() != null) {
            sql.SET("gender = #{gender,jdbcType=CHAR}");
        }
        
        if (record.getBirthday() != null) {
            sql.SET("birthday = #{birthday,jdbcType=CHAR}");
        }
        
        if (record.getHeadurl() != null) {
            sql.SET("headUrl = #{headurl,jdbcType=VARCHAR}");
        }
        
        if (record.getPlace() != null) {
            sql.SET("place = #{place,jdbcType=VARCHAR}");
        }
        
        if (record.getWeibonum() != null) {
            sql.SET("weiboNum = #{weibonum,jdbcType=INTEGER}");
        }
        
        if (record.getConnum() != null) {
            sql.SET("conNum = #{connum,jdbcType=INTEGER}");
        }
        
        if (record.getFansnum() != null) {
            sql.SET("fansNum = #{fansnum,jdbcType=INTEGER}");
        }
        
        if (record.getMarriage() != null) {
            sql.SET("marriage = #{marriage,jdbcType=CHAR}");
        }
        
        if (record.getSignature() != null) {
            sql.SET("signature = #{signature,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("uid = #{uid,jdbcType=CHAR}");
        
        return sql.toString();
    }
}