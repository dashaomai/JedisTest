package com.clevercells.basicData;

import com.clevercells.interfaces.IKey;

/**
 * 用户基础数据的 Key 对象
 * Created by dasha on 2017/6/9.
 */
public class BasicDataKey implements IKey {
    public final int gameId;
    public final long userId;

    private final String key;

    public BasicDataKey(
            final int gameId,
            final long userId
    ) {
        this.gameId = gameId;
        this.userId = userId;

        key = "game_" + gameId + ":user_" + userId + ":bd";
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getChangeListKey() {
        return "game_" + gameId + ":bd";
    }

    @Override
    public String getId() {
        return "game_" + gameId + ":user_" + userId;
    }
}
