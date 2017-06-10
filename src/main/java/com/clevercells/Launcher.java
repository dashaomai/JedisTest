package com.clevercells;

import com.clevercells.redis.RedisManager;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by dasha on 2017/6/8.
 */
public class Launcher {

    private static final String ITEM_PREFIX = "item_";
    private static final int ITEM_PREFIX_LENGTH = ITEM_PREFIX.length();
    private static final String ITEM_MATCH = ITEM_PREFIX + "*";

    private static final String CURSOR_END = "0";

    public static void main(String[] args) {
        final String key = "data_10000:user_35:bp_101";

        opeartionByManager(key);
    }

    private static void createAndQueryItems(final ShardedJedisPool pool, final String key) {
        final ShardedJedis jedis = pool.getResource();

        final ShardedJedisPipeline pipeline = jedis.pipelined();

        pipeline.hset(key, "id", "101");

        for (int i = 135, m = 235; i<m; i++) {
            pipeline.hset(key, ITEM_PREFIX + i, "what is the content is not import");
        }

        pipeline.sync();

        final ScanParams params = new ScanParams();
        params.match(ITEM_MATCH);

        String cursor = CURSOR_END;
        final Map<Integer, String> items = new HashMap<>();

        do {
            final ScanResult<Map.Entry<String, String>> result = jedis.hscan(key, cursor, params);
            final List<Map.Entry<String, String>> results = result.getResult();

            for (Map.Entry<String, String> entry : results) {
                final Integer itemId = Integer.parseInt(entry.getKey().substring(ITEM_PREFIX_LENGTH));
                final String itemContent = entry.getValue();

                items.put(itemId, itemContent);
            }

            cursor = result.getStringCursor();

            System.out.println("cursor = " + cursor);
        } while (!CURSOR_END.equals(cursor));

        jedis.close();

        final int count = items.size();

        System.out.println("count = " + count);
    }

    private static void incVersionsForBackpack(final ShardedJedisPool pool, final String key) {
        final ShardedJedis jedis = pool.getResource();

        jedis.hincrBy(key, "ver", 1);

        jedis.close();
    }

    private static void opeartionByManager(final String key) {
        final JedisCommands client = RedisManager.GetClient();

        System.out.println(client.exists(key));

        RedisManager.ReleaseClient(client);
    }
}
