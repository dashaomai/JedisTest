package com.clevercells.basicData;

import com.clevercells.common.IKeyable;

/**
 *
 * Created by dasha on 2017/6/9.
 */
public class BasicDataKeyable implements IKeyable {
    public final int gameId;
    public final long userId;

    private final String key;

    public BasicDataKeyable(
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
}
