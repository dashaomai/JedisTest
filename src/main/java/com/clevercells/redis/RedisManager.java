package com.clevercells.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Redis 管理器类
 * Created by dasha on 2017/6/9.
 */
public class RedisManager {
    private static final Logger log = LoggerFactory.getLogger("RedisManager");

    private static final JedisPool pool = new JedisPool("localhost", 6379);

    public static Jedis GetClient() {
        log.info("获取 Redis 连接");

        try {
            return pool.getResource();
        } catch (Exception ex) {
            log.warn("无法获取 Redis 客户端：{}", ex.getLocalizedMessage());

            return null;
        }
    }

    public static void ReleaseClient(final Jedis client) {
        if (null != client) {
            log.info("归还 Redis 连接");
            client.close();
        }
    }

    public static <T> T Execute(final IRedisExecution<T> execution) {
        Jedis client = GetClient();

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
