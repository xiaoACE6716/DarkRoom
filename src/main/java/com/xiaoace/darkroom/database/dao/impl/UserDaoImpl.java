package com.xiaoace.darkroom.database.dao.impl;

import com.xiaoace.darkroom.database.dao.UserDao;
import com.xiaoace.darkroom.database.pojo.User;
import com.xiaoace.darkroom.database.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public Integer createTable() {
        try {
            SqlSession session = MyBatisUtil.getSqlSession();
            UserDao userDao = session.getMapper(UserDao.class);
            Integer result = userDao.createTable();
            session.commit();
            session.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Integer dropTable() {
        try {
            SqlSession session = MyBatisUtil.getSqlSession();
            UserDao userDao = session.getMapper(UserDao.class);
            Integer result = userDao.dropTable();
            session.commit();
            session.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<User> selectAllUser() {
        List<User> users = null;
        try {
            SqlSession session = MyBatisUtil.getSqlSession();
            UserDao userDao = session.getMapper(UserDao.class);
            users = userDao.selectAllUser();
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User selectUserById(String id) {
        User user = null;
        try {
            SqlSession session = MyBatisUtil.getSqlSession();
            UserDao userDao = session.getMapper(UserDao.class);
            user = userDao.selectUserById(id);
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Integer deleteUserById(String id) {
        try {
            SqlSession session = MyBatisUtil.getSqlSession();
            UserDao userDao = session.getMapper(UserDao.class);
            Integer result = userDao.deleteUserById(id);
            session.commit();
            session.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Integer insert(User user) {
        try {
            SqlSession session = MyBatisUtil.getSqlSession();
            UserDao userDao = session.getMapper(UserDao.class);
            Integer result = userDao.insert(user);
            session.commit();
            session.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
