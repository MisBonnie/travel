package com.zzxx.travel.dao;

import com.zzxx.travel.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserDao {

    @Select("select * from tab_user where username = #{username} and password = #{password}")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Insert("insert into tab_user(username, password, name, birthday, sex, telephone, email, status, code)" +
            " values(#{username},#{password},#{name},#{birthday},#{sex},#{telephone},#{email},#{status},#{code})")
    void saveUser(User user);

    @Select("select * from tab_user where username = #{username}")
    User findUserByUsername(String username);

    @Update("update tab_user set status = 'Y' where code = #{code}")
    void updateUserStatus(String code);
}
