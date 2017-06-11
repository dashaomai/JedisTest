package com.clevercells.redis;

import redis.clients.jedis.JedisCommands;

/**
 * Created by Bob Jiang on 2017/6/11.
 */
public interface IRedisExecution<T> {
    T execute(final JedisCommands client);
}
