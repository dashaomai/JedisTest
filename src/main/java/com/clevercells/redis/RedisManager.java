package com.clevercells.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;

import java.io.Closeable;
import java.io.IOException;

/**
 * Redis 管理器类
 * Created by dasha on 2017/6/9.
 */
public class RedisManager {
    private static final Logger log = LoggerFactory.getLogger("RedisManager");

    private static final JedisPool pool = new JedisPool("localhost", 6379);

    public static JedisCommands GetClient() {
        log.info("获取 Redis 连接");

        try {
            return pool.getResource();
        } catch (Exception ex) {
            log.warn("无法获取 Redis 客户端：{}", ex.getLocalizedMessage());

            return null;
        }
    }

    public static void ReleaseClient(final JedisCommands client) {
        if (null != client) {
            log.info("归还 Redis 连接");
            final Closeable client2 = (Closeable)client;
            try {
                client2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> T Execute(final IRedisExecution<T> execution) {
        JedisCommands client = GetClient();

        if (null != client) {
            try {
                final T result = execution.execute(client);

                ReleaseClient(client);
                client = null;

                return result;
            } catch (Exception ex) {
                log.error("执行 Redis 操作时出错：{}", ex.getLocalizedMessage());

                return null;
            } finally {
                if (null != client) {
                    ReleaseClient(client);
                }
            }
        } else {
            return null;
        }
    }
}
