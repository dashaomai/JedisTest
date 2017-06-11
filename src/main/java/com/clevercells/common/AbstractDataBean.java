package com.clevercells.common;

import com.clevercells.redis.RedisManager;
import redis.clients.jedis.JedisCommands;

/**
 * Created by Bob Jiang on 2017/6/11.
 */
public abstract class AbstractDataBean<K extends IKeyable> implements IDataBean, IMetable<K> {
    protected final IKeyable keyable;

    protected AbstractDataBean(
            final IKeyable keyable
    ) {
        this.keyable = keyable;
    }

    @Override
    public DataMeta getMeta() {
        return null;
    }

    @Override
    public void setChange() {
        final JedisCommands client = RedisManager.GetClient();

        if (null != client) {

        }
    }
}
