package org.nefu.softlab.weiboAPI.core.DAO.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.nefu.softlab.weiboAPI.core.DAO.mapper.provider.UserSqlProvider;
import org.nefu.softlab.weiboAPI.core.PO.User;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    // write

    /**
     * 根据用户名和密码查询用户
     * @return user/null
     */
    @Select("SELECT * FROM `t_user` WHERE `username`=#{username} AND `passwd`=MD5(#{passwd})")
    User selectUserByUsernameAndPasswd(User user);

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    @Select("SELECT * FROM `t_user` WHERE `username`=#{username}")
    User selectUserByUsername(String username);

    /**
     * 根据token获取用户
     * @param token
     * @return
     */
    @Select("SELECT * FROM `t_user` WHERE `token`=#{token}")
    User selectUserByToken(String token);


    // auto generated
    @Delete({
        "delete from t_user",
        "where uid = #{uid,jdbcType=CHAR}"
    })
    int deleteByPrimaryKey(String uid);

    @Insert({
        "insert into t_user (uid, username, ",
        "passwd, token, lastLogin)",
        "values (#{uid,jdbcType=CHAR}, #{username,jdbcType=VARCHAR}, ",
        "#{passwd,jdbcType=CHAR}, #{token,jdbcType=CHAR}, #{lastlogin,jdbcType=VARCHAR})"
    })
    int insert(User record);

    @InsertProvider(type=UserSqlProvider.class, method="insertSelective")
    int insertSelective(User record);

    @Select({
        "select",
        "uid, username, passwd, token, lastLogin",
        "from t_user",
        "where uid = #{uid,jdbcType=CHAR}"
    })
    @Results({
        @Result(column="uid", property="uid", jdbcType=JdbcType.CHAR, id=true),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="passwd", property="passwd", jdbcType=JdbcType.CHAR),
        @Result(column="token", property="token", jdbcType=JdbcType.CHAR),
        @Result(column="lastLogin", property="lastlogin", jdbcType=JdbcType.VARCHAR)
    })
    User selectByPrimaryKey(String uid);

    @UpdateProvider(type=UserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(User record);

    @Update({
        "update t_user",
        "set username = #{username,jdbcType=VARCHAR},",
          "passwd = #{passwd,jdbcType=CHAR},",
          "token = #{token,jdbcType=CHAR},",
          "lastLogin = #{lastlogin,jdbcType=VARCHAR}",
        "where uid = #{uid,jdbcType=CHAR}"
    })
    int updateByPrimaryKey(User record);
}