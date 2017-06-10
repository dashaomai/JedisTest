package com.clevercells.common;

import java.io.Serializable;

/**
 * 数据的生命周期管理元数据
 * Created by dasha on 2017/6/9.
 */
public class DataMeta implements Serializable {
    public static final String IS_NEW = "meta_new";
    public static final String VERSION = "meta_ver";
    public static final String DB_VERSION = "meta_dbver";

    public boolean isNew;
    public int version;
    public int dbVersion;

    public DataMeta(
            final boolean isNew,
            final int version,
            final int dbVersion
    ) {
        this.isNew = isNew;
        this.version = version;
        this.dbVersion = dbVersion;
    }

    public DataMeta(
            final boolean isNew
    ) {
        this(isNew, 0, 0);
    }

    public DataMeta() {
        this(true);
    }
}
