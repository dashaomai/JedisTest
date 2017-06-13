package com.clevercells.common;

import com.clevercells.basicData.BasicDataBean;

/**
 * 用于获取各类数据 Bean 实例的工厂类
 *
 * Created by Bob Jiang on 2017/6/12.
 */
public class RedisData {

    public static BasicDataBean CreateBasicDataBean(
            final int gameId,
            final long userId,
            final String nickname,
            final int avatar,
            final int level,
            final int grade,
            final int money,
            final int gender,
            final long referee,
            final String helpFlag,
            final String workFlag,
            final String usernameInPlatform,
            final String nicknameInPlatform,
            final String avatarInPlatform
    ) {
        final BasicDataBean bean = new BasicDataBean(gameId, userId);
        bean.nickname = nickname;
        bean.avatar = avatar;
        bean.level = level;
        bean.grade = grade;
        bean.money = money;
        bean.gender = gender;
        bean.referee = referee;
        bean.helpFlag = helpFlag;
        bean.workFlag = workFlag;
        bean.usernameInPlatform = usernameInPlatform;
        bean.nicknameInPlatform = nicknameInPlatform;
        bean.avatarInPlatform = avatarInPlatform;

        return bean;
    }

    public static BasicDataBean GetBasicDataBean(final int gameId, final long userId) {
        final BasicDataBean bean = new BasicDataBean(gameId, userId);
        if (bean.LoadFromRedis()) {
            return bean;
        } else {
            return null;
        }
    }

    public static Boolean Save(final AbstractBean bean) {
        return bean.SaveToRedis();
    }
}
