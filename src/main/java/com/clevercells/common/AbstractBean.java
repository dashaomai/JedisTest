package com.clevercells.common;

import com.clevercells.annotations.RedisCachable;
import com.clevercells.interfaces.IKey;
import com.clevercells.redis.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bean 的抽象基类
 * Created by Bob Jiang on 2017/6/12.
 */
public abstract class AbstractBean<K extends IKey> {
    private static final Logger log = LoggerFactory.getLogger("AbstractBean");

    protected static Field[] redisCachableFields = null;

    /**
     * 扫描并装配本类当中，使用了 RedisCachable 注解的所有成员变量
     *
     * @param clazz
     * @return
     */
    private static Field[] ScanRedisCachableFields(Class clazz) {
        final Field[] fields = clazz.getFields();

        final List<Field> annotatedFields = new ArrayList<>();
        for (final Field field : fields) {
            if (field.isAnnotationPresent(RedisCachable.class)) {
                annotatedFields.add(field);

                log.debug("添加了 {} 类的RedisCacheable 属性 {}，类型为 {}", clazz.getName(), field.getName(), field.getType().getTypeName());
            }
        }

        final Field[] results = new Field[annotatedFields.size()];

        return annotatedFields.toArray(results);
    }

    /**
     * 初始化注解的所有成员变量
     */
    private synchronized void InitAnnotations() {
        if (null == redisCachableFields) {
            redisCachableFields = ScanRedisCachableFields(this.getClass());
        }
    }

    protected final K key;

    protected AbstractBean(
            final K key
    ) {
        if (null == redisCachableFields) {
            InitAnnotations();
        }

        this.key = key;
    }

    public Boolean LoadFromRedis() {
        final String key1 = key.getKey();

        return RedisManager.Execute(client -> {
            for (final Field field : redisCachableFields) {
                final String name = field.getName();
                final String typeName = field.getType().getTypeName();

                final String value = client.hget(key1, name);

                field.setAccessible(true);

                try {
                    if ("int".equals(typeName)) {
                        field.setInt(this, (null != value) ? Integer.parseInt(value) : 0);
                    } else if ("long".equals(typeName)) {
                        field.setLong(this, (null != value) ? Long.parseLong(value) : 0L);
                    } else if ("java.lang.String".equals(typeName)) {
                        field.set(this, (null != value) ? value : "");
                    }
                } catch (IllegalAccessException e) {
                    log.error("为实例 {} 设置 {} 值时出错：{}", this, name, e.getLocalizedMessage());

                    return false;
                }
            }

            return true;
        });
    }

    public Boolean SaveToRedis() {
        final String key1 = key.getKey();

        return RedisManager.Execute(client -> {
            final Map<String, String> values = new HashMap<>();

            for (final Field field : redisCachableFields) {
                final String name = field.getName();
                final String typeName = field.getType().getTypeName();

                try {
                    if ("int".equals(typeName)) {
                        values.put(name, String.valueOf(field.getInt(this)));
                    } else if ("long".equals(typeName)) {
                        values.put(name, String.valueOf(field.getLong(this)));
                    } else if ("java.lang.String".equals(typeName)) {
                        values.put(name, field.get(this).toString());
                    }
                } catch (IllegalAccessException e) {
                    log.error("从实例 {} 读取 {} 值时出错：{}", this, name, e.getLocalizedMessage());

                    return false;
                }
            }

            client.hmset(key1, values);

            return true;
        });
    }
}
