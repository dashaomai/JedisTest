package com.clevercells.basicData.manager;

import com.clevercells.common.DataMeta;
import com.clevercells.common.IDataReader;
import com.clevercells.basicData.BasicDataBean;
import com.clevercells.basicData.BasicDataKeyable;
import com.clevercells.redis.RedisManager;
import redis.clients.jedis.JedisCommands;

/**
 * 玩家基础数据读取类
 * Created by dasha on 2017/6/9.
 */
public class BasicDataReader implements IDataReader<BasicDataBean, BasicDataKeyable> {
  private static BasicDataReader _instance = new BasicDataReader();

  public static BasicDataReader getInstance() {
    return _instance;
  }

  private BasicDataReader() {

  }

  @Override
  public BasicDataBean getData(final BasicDataKeyable keyable) {
    final JedisCommands client = RedisManager.GetClient();

    if (null != client) {
      final String key = keyable.getKey();

      try {
        if (client.exists(key)) {
        } else {
          return null;
        }
      } catch (Exception ex) {
        return null;
      } finally {
        RedisManager.ReleaseClient(client);
      }
      return null;
    } else {
      return null;
    }
  }

  @Override
  public DataMeta getMeta() {
    return null;
  }

  @Override
  public void setChange() {

  }
}
