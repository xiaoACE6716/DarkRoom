package com.xiaoace.darkroom.database.dao;

import com.xiaoace.darkroom.database.pojo.User;

import java.util.List;

public interface UserDao {

    Integer createTable();

    Integer dropTable();

    List<User> selectAllUser();

    User selectUserById(String id);

    Integer deleteUserById(String id);

    Integer insert(User user);

}
