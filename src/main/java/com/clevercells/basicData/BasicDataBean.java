package com.clevercells.basicData;

import com.clevercells.annotations.RedisCachable;
import com.clevercells.common.AbstractBean;

/**
 *
 * Created by dasha on 2017/6/9.
 */
public class BasicDataBean extends AbstractBean<BasicDataKey> {

    @RedisCachable
    public String nickname;
    @RedisCachable
    public int avatar;
    @RedisCachable
    public int level;
    @RedisCachable
    public int grade;
    @RedisCachable
    public int money;
    @RedisCachable
    public int gender;
    @RedisCachable
    public long referee;

    @RedisCachable
    public String helpFlag;
    @RedisCachable
    public String workFlag;

    @RedisCachable
    public String usernameInPlatform;
    @RedisCachable
    public String nicknameInPlatform;
    @RedisCachable
    public String avatarInPlatform;

    public BasicDataBean(
            final int gameId,
            final long userId
    ) {
        super(new BasicDataKey(gameId, userId));
    }
}
