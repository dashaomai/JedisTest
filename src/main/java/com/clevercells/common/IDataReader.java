package com.clevercells.common;

/**
 * 数据管理者接口
 * Created by dasha on 2017/6/9.
 */
public interface IDataReader<T extends IDataBean, K extends IKeyable> extends IMetable<K> {
    T getData(final K keyable);
}
