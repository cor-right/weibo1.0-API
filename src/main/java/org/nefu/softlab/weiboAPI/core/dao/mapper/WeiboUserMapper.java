package org.nefu.softlab.weiboAPI.core.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.nefu.softlab.weiboAPI.core.dao.mapper.provider.WeiboUserSqlProvider;
import org.nefu.softlab.weiboAPI.core.po.User;
import org.nefu.softlab.weiboAPI.core.po.WeiboUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Jiaxu_Zou on 2018-4-7
 */
@Mapper
@Repository
public interface WeiboUserMapper {

    // written

    @Select("SELECT * FROM t_weibo_user ORDER BY `fansNum` DESC LIMIT 50")
    List<WeiboUser> getFamousUsers();

    @Select("SELECT COUNT(uid) FROM t_weibo_user")
    Long selectUserCount();

    // generated
    @Delete({
        "delete from t_weibo_user",
        "where uid = #{uid,jdbcType=CHAR}"
    })
    int deleteByPrimaryKey(String uid);

    @Insert({
        "insert into t_weibo_user (uid, nickName, ",
        "gender, birthday, headUrl, ",
        "place, weiboNum, ",
        "conNum, fansNum, ",
        "marriage, signature)",
        "values (#{uid,jdbcType=CHAR}, #{nickname,jdbcType=VARCHAR}, ",
        "#{gender,jdbcType=CHAR}, #{birthday,jdbcType=CHAR}, #{headurl,jdbcType=VARCHAR}, ",
        "#{place,jdbcType=VARCHAR}, #{weibonum,jdbcType=INTEGER}, ",
        "#{connum,jdbcType=INTEGER}, #{fansnum,jdbcType=INTEGER}, ",
        "#{marriage,jdbcType=CHAR}, #{signature,jdbcType=VARCHAR})"
    })
    int insert(WeiboUser record);

    @InsertProvider(type=WeiboUserSqlProvider.class, method="insertSelective")
    int insertSelective(WeiboUser record);

    @Select({
        "select",
        "uid, nickName, gender, birthday, headUrl, place, weiboNum, conNum, fansNum, ",
        "marriage, signature",
        "from t_weibo_user",
        "where uid = #{uid,jdbcType=CHAR}"
    })
    @Results({
        @Result(column="uid", property="uid", jdbcType=JdbcType.CHAR, id=true),
        @Result(column="nickName", property="nickname", jdbcType=JdbcType.VARCHAR),
        @Result(column="gender", property="gender", jdbcType=JdbcType.CHAR),
        @Result(column="birthday", property="birthday", jdbcType=JdbcType.CHAR),
        @Result(column="headUrl", property="headurl", jdbcType=JdbcType.VARCHAR),
        @Result(column="place", property="place", jdbcType=JdbcType.VARCHAR),
        @Result(column="weiboNum", property="weibonum", jdbcType=JdbcType.INTEGER),
        @Result(column="conNum", property="connum", jdbcType=JdbcType.INTEGER),
        @Result(column="fansNum", property="fansnum", jdbcType=JdbcType.INTEGER),
        @Result(column="marriage", property="marriage", jdbcType=JdbcType.CHAR),
        @Result(column="signature", property="signature", jdbcType=JdbcType.VARCHAR)
    })
    WeiboUser selectByPrimaryKey(String uid);

    @UpdateProvider(type=WeiboUserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(WeiboUser record);

    @Update({
        "update t_weibo_user",
        "set nickName = #{nickname,jdbcType=VARCHAR},",
          "gender = #{gender,jdbcType=CHAR},",
          "birthday = #{birthday,jdbcType=CHAR},",
          "headUrl = #{headurl,jdbcType=VARCHAR},",
          "place = #{place,jdbcType=VARCHAR},",
          "weiboNum = #{weibonum,jdbcType=INTEGER},",
          "conNum = #{connum,jdbcType=INTEGER},",
          "fansNum = #{fansnum,jdbcType=INTEGER},",
          "marriage = #{marriage,jdbcType=CHAR},",
          "signature = #{signature,jdbcType=VARCHAR}",
        "where uid = #{uid,jdbcType=CHAR}"
    })
    int updateByPrimaryKey(WeiboUser record);
}