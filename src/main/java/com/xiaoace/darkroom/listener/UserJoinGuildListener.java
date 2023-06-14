package com.xiaoace.darkroom.listener;

import com.xiaoace.darkroom.Main;
import com.xiaoace.darkroom.database.dao.UserDao;
import com.xiaoace.darkroom.database.dao.impl.UserDaoImpl;
import com.xiaoace.darkroom.database.pojo.User;
import snw.jkook.event.EventHandler;
import snw.jkook.event.Listener;
import snw.jkook.event.user.UserJoinGuildEvent;

public class UserJoinGuildListener implements Listener {

    UserDao userDao = new UserDaoImpl();

    @EventHandler
    public void onUserJoinGuildEvent(UserJoinGuildEvent event) {

        String userID = event.getUser().getId();

        User user = userDao.selectUserById(userID);

        if (user.getId().equals(userID)) {
            event.getUser().grantRole(Main.getRole());
        }

    }

}
