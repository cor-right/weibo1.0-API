package org.nefu.softlab.weiboAPI.core.DAO.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.nefu.softlab.weiboAPI.core.DAO.mapper.provider.WeiboDataSqlProvider;
import org.nefu.softlab.weiboAPI.core.PO.WeiboData;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WeiboDataMapper {
    @Delete({
        "delete from t_weibo_data",
        "where wid = #{wid,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String wid);

    @Insert({
        "insert into t_weibo_data (wid, attu, ",
        "comment, device, ",
        "time, rewid, uid, ",
        "content)",
        "values (#{wid,jdbcType=VARCHAR}, #{attu,jdbcType=INTEGER}, ",
        "#{comment,jdbcType=INTEGER}, #{device,jdbcType=VARCHAR}, ",
        "#{time,jdbcType=BIGINT}, #{rewid,jdbcType=VARCHAR}, #{uid,jdbcType=VARCHAR}, ",
        "#{content,jdbcType=LONGVARCHAR})"
    })
    int insert(WeiboData record);

    @InsertProvider(type=WeiboDataSqlProvider.class, method="insertSelective")
    int insertSelective(WeiboData record);

    @Select({
        "select",
        "wid, attu, comment, device, time, rewid, uid, content",
        "from t_weibo_data",
        "where wid = #{wid,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="wid", property="wid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="attu", property="attu", jdbcType=JdbcType.INTEGER),
        @Result(column="comment", property="comment", jdbcType=JdbcType.INTEGER),
        @Result(column="device", property="device", jdbcType=JdbcType.VARCHAR),
        @Result(column="time", property="time", jdbcType=JdbcType.BIGINT),
        @Result(column="rewid", property="rewid", jdbcType=JdbcType.VARCHAR),
        @Result(column="uid", property="uid", jdbcType=JdbcType.VARCHAR),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR)
    })
    WeiboData selectByPrimaryKey(String wid);

    @UpdateProvider(type=WeiboDataSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(WeiboData record);

    @Update({
        "update t_weibo_data",
        "set attu = #{attu,jdbcType=INTEGER},",
          "comment = #{comment,jdbcType=INTEGER},",
          "device = #{device,jdbcType=VARCHAR},",
          "time = #{time,jdbcType=BIGINT},",
          "rewid = #{rewid,jdbcType=VARCHAR},",
          "uid = #{uid,jdbcType=VARCHAR},",
          "content = #{content,jdbcType=LONGVARCHAR}",
        "where wid = #{wid,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKeyWithBLOBs(WeiboData record);

    @Update({
        "update t_weibo_data",
        "set attu = #{attu,jdbcType=INTEGER},",
          "comment = #{comment,jdbcType=INTEGER},",
          "device = #{device,jdbcType=VARCHAR},",
          "time = #{time,jdbcType=BIGINT},",
          "rewid = #{rewid,jdbcType=VARCHAR},",
          "uid = #{uid,jdbcType=VARCHAR}",
        "where wid = #{wid,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(WeiboData record);
}