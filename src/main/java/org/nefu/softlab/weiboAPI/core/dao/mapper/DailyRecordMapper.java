package org.nefu.softlab.weiboAPI.core.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.nefu.softlab.weiboAPI.core.dao.mapper.provider.DailyRecordSqlProvider;
import org.nefu.softlab.weiboAPI.core.po.DailyRecord;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DailyRecordMapper {

    // written

    /**
     * 获取最近七天的总数据量记录
     * @return
     */
    @Select("SELECT * FROM t_monitor_record_daily WHERE `socket`='0.0.0.0:27017' ORDER BY `date` ASC ,`socket` ASC LIMIT 7")
    List<DailyRecord> getLastSevenDayRecord();

    /**
     * 获取当前最近的当天的数据量记录
     * @return
     */
    @Select("SELECT * FROM `t_monitor_record_daily` WHERE `date`=(SELECT MAX(`date`) FROM `t_monitor_record_daily`) ORDER BY `saveTimestamp` DESC, `socket` DESC LIMIT 0,4")
    List<DailyRecord> getLastDayRecord();

    // generated

    @Delete({
        "delete from t_monitor_record_daily",
        "where rid = #{rid,jdbcType=CHAR}"
    })
    int deleteByPrimaryKey(String rid);

    @Insert({
        "insert into t_monitor_record_daily (rid, socket, ",
        "recordnumber, recordsize, ",
        "date, saveTimestamp)",
        "values (#{rid,jdbcType=CHAR}, #{socket,jdbcType=VARCHAR}, ",
        "#{recordnumber,jdbcType=INTEGER}, #{recordsize,jdbcType=DOUBLE}, ",
        "#{date,jdbcType=CHAR}, #{savetimestamp,jdbcType=VARCHAR})"
    })
    int insert(DailyRecord record);

    @InsertProvider(type=DailyRecordSqlProvider.class, method="insertSelective")
    int insertSelective(DailyRecord record);

    @Select({
        "select",
        "rid, socket, recordnumber, recordsize, date, saveTimestamp",
        "from t_monitor_record_daily",
        "where rid = #{rid,jdbcType=CHAR}"
    })
    @Results({
        @Result(column="rid", property="rid", jdbcType=JdbcType.CHAR, id=true),
        @Result(column="socket", property="socket", jdbcType=JdbcType.VARCHAR),
        @Result(column="recordnumber", property="recordnumber", jdbcType=JdbcType.INTEGER),
        @Result(column="recordsize", property="recordsize", jdbcType=JdbcType.DOUBLE),
        @Result(column="date", property="date", jdbcType=JdbcType.CHAR),
        @Result(column="saveTimestamp", property="savetimestamp", jdbcType=JdbcType.VARCHAR)
    })
    DailyRecord selectByPrimaryKey(String rid);

    @UpdateProvider(type=DailyRecordSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DailyRecord record);

    @Update({
        "update t_monitor_record_daily",
        "set socket = #{socket,jdbcType=VARCHAR},",
          "recordnumber = #{recordnumber,jdbcType=INTEGER},",
          "recordsize = #{recordsize,jdbcType=DOUBLE},",
          "date = #{date,jdbcType=CHAR},",
          "saveTimestamp = #{savetimestamp,jdbcType=VARCHAR}",
        "where rid = #{rid,jdbcType=CHAR}"
    })
    int updateByPrimaryKey(DailyRecord record);
}