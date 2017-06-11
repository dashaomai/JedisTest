package com.clevercells.common;

/**
 * Created by dasha on 2017/6/10.
 */
public interface IMetable<K extends IKeyable> {
    DataMeta getMeta();
    void setChange();
}
