package com.clevercells.common;

import com.clevercells.redis.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCommands;

/**
 * 访问 Meta 生命周期元数据的类
 * Created by dasha on 2017/6/10.
 */
public class MetaReader<K extends IKeyable> implements IMetable<K> {
    private static final Logger log = LoggerFactory.getLogger("MetaReader");

    @Override
    public DataMeta getMeta(final K keyable) {
        final String key = keyable.getKey();

        final JedisCommands client = RedisManager.GetClient();

        if (null != client) {
            if (client.exists(key)) {
                final boolean isNew = !"0".equals(client.hget(key, DataMeta.IS_NEW));
                final int version = Integer.parseInt(client.hget(key, DataMeta.VERSION));
                final int dbVersion = Integer.parseInt(client.hget(key, DataMeta.DB_VERSION));

                RedisManager.ReleaseClient(client);

                return new DataMeta(isNew, version, dbVersion);
            } else {
                RedisManager.ReleaseClient(client);

                return null;
            }
        } else {
            return null;
        }
    }
}
