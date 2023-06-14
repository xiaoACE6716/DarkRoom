package com.xiaoace.darkroom;

import com.xiaoace.darkroom.database.dao.UserDao;
import com.xiaoace.darkroom.database.dao.impl.UserDaoImpl;
import com.xiaoace.darkroom.database.pojo.User;
import com.xiaoace.darkroom.listener.UserJoinGuildListener;
import snw.jkook.command.JKookCommand;
import snw.jkook.entity.Guild;
import snw.jkook.entity.Role;
import snw.jkook.plugin.BasePlugin;
import snw.jkook.util.PageIterator;

import java.io.File;
import java.util.List;
import java.util.Set;

import static com.xiaoace.darkroom.utils.Tools.addListener;

public class Main extends BasePlugin {

    private static Main INSTANCE;

    public static Main getInstance() {
        return INSTANCE;
    }

    private static Guild guild = null;

    public static Guild getGuild() {
        return guild;
    }

    public static void setGuild(Guild guild) {
        Main.guild = guild;
    }

    private static Role role = null;

    public static Role getRole() {
        return role;
    }

    public static void setRole(Role role) {
        Main.role = role;
    }

    @Override
    public void onLoad() {
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        INSTANCE = this;

        //初始化服务器
        String Guild_ID = getConfig().getString("Guild_ID");
        if (Guild_ID == null || Guild_ID.length() == 0) {
            throw new Error("你还没有提供Guild ID哦");
        } else {
            setGuild(getCore().getHttpAPI().getGuild(Guild_ID));
        }
        //初始化小黑屋角色
        String roleName = getConfig().getString("Dark_Room_Role_Name");
        if (roleName == null || roleName.length() == 0) {
            throw new Error("你还没有提供小黑屋角色的名字哦");
        } else {
            PageIterator<Set<Role>> roles = null;
            if (guild != null) {
                roles = guild.getRoles();
            }
            if (roles != null) {
                while (roles.hasNext()) {
                    Set<Role> roleSet = roles.next();
                    for (Role theRole : roleSet) {
                        if (theRole.getName().equals(roleName)) {
                            setRole(theRole);
                        }
                    }
                }
            }
        }
        //检查一下是否正确拿到角色
        if (getRole() == null) {
            throw new Error("获取不到对应的角色，请检查配置文件里的各项配置有没有正确填写");
        }

        UserDao userDao = new UserDaoImpl();
        //尝试初始化数据库
        File userDB = new File(getDataFolder(), "user.db");
        if (!userDB.exists()) {
            userDao.createTable();
        }


        new JKookCommand("ban", "/")
                .setDescription("用法: /ban <KOOK用户ID>")
                .setHelpContent("用法: /ban <KOOK用户ID>")
                .addArgument(String.class)
                .executesUser((sender, objects, message) -> {

                    //判断这个人有没有权限使用这个命令
                    String senderID = sender.getId();
                    List<String> adminList = getConfig().getStringList("Admin");
                    if (adminList.contains(senderID)) {
                        //给ban命令指向的用户添加关小黑屋专用角色
                        getCore().getHttpAPI().getUser(objects[0].toString()).grantRole(getRole());
                        //并且添加到数据库
                        userDao.insert(new User(objects[0].toString()));
                        getLogger().info(sender.getName() +"ban了 " + objects[0].toString());
                    }else {
                        getLogger().info(sender.getName() +"尝试ban " + objects[0].toString() +"但是TA没有权限");
                    }
                }).register(this);

        new JKookCommand("unban", "/")
                .setDescription("用法: /unban <KOOK用户ID>")
                .setHelpContent("用法: /unban <KOOK用户ID>")
                .addArgument(String.class)
                .executesUser((sender, objects, message) -> {

                    //判断这个人有没有权限使用这个命令
                    String senderID = sender.getId();
                    List<String> adminList = getConfig().getStringList("Admin");
                    if (adminList.contains(senderID)) {
                        //给unban命令指向的用户移除掉关小黑屋专用角色
                        getCore().getHttpAPI().getUser(objects[0].toString()).revokeRole(getRole());
                        //并且删除数据库里的记录
                        userDao.deleteUserById(objects[0].toString());
                        getLogger().info(sender.getName() +"unban了 " + objects[0].toString());
                    }else {
                        getLogger().info(sender.getName() +"尝试unban " + objects[0].toString() +"但是TA没有权限");
                    }
                }).register(this);

        //监听器
        addListener(new UserJoinGuildListener());

    }
}
