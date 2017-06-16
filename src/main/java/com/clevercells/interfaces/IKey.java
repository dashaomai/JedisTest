package com.clevercells.interfaces;

/**
 * 主键对象接口
 *
 * 该接口用于具体的数据实体在 Redis 内主键的生成接口
 *
 * Created by Bob Jiang on 2017/6/11.
 */
public interface IKey {
    /**
     * 获取当前实例的主键
     * @return          在 Redis 内检索的主键
     */
    String getKey();

    /**
     * 获取变更列表的主键
     * @return          变更列表在 Redis 内检索的主键
     */
    String getChangeListKey();

    /**
     * 获取当前实例的 ID
     * @return          当前实例存储于变更列表内的 ID
     */
    String getId();
}
