package com.clevercells.basicData;

import com.clevercells.common.AbstractDataBean;
import com.clevercells.common.DataMeta;
import com.clevercells.common.IDataBean;
import com.clevercells.common.IMetable;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;

/**
 *
 * Created by dasha on 2017/6/9.
 */
public class BasicDataBean extends AbstractDataBean<BasicDataKeyable> {
    public static final String NICKNAME = "nkname";
    public static final String AVATAR = "avatar";

    String nickname;
    int level;

    public BasicDataBean(
            final BasicDataKeyable keyable
    ) {
        super(keyable);
    }

    @Override
    public String getKey() {
        return null;
    }
}
