package com.ruoyi.YDOnlineTaxi.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis操控数据库的工具类
 */
@Component
public final class RedisUtils {

    @Autowired(required = false)
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置键值对在数据库中存在的时间，单位为秒
     *
     * @param key  需要设置时间的键名
     * @param time 需要设置的时间
     * @return true or false 表示成功或失败
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0)
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查看数据库中key的剩余时间
     *
     * @param key 键名
     * @return 剩余时间
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断是否存在key的值
     *
     * @param key 键名
     * @return true or false 表示存在或不存在
     */
    public boolean hasKey(String key) {
        try {
            Boolean hasKey = redisTemplate.hasKey(key);
            if (hasKey != null) {
                return hasKey;
            } else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 键名，可传一个或多个
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1)
                redisTemplate.delete(key[0]);
            else
                redisTemplate.delete(String.valueOf(CollectionUtils.arrayToList(key)));
        }
    }

    /**
     * 查找key下的所有值
     *
     * @param key 键名
     * @return key对应的所有value
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查找是否有key-value键值对存在
     *
     * @param key   键名
     * @param value 值
     * @return true or false
     */
    public boolean sHasKey(String key, Object value) {
        try {
            Boolean member = redisTemplate.opsForSet().isMember(key, value);
            if (member != null)
                return member;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入key set中
     *
     * @param key    键
     * @param values 值，可以是多个也可以是一个
     * @return 成功插入的个数
     */
    public long sSet(String key, Object... values) {
        try {
            Long add = redisTemplate.opsForSet().add(key, values);
            if (add != null){
                if(add!=0)
                    return add;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将数据添加key set 并设置缓存时间
     *
     * @param key    键
     * @param time   时间
     * @param values 存入的对象
     * @return 操作成功的个数
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long add = redisTemplate.opsForSet().add(key, values);
            if (time > 0)
                expire(key, time);
            if (add != null)
                return add;
            else
                return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取集合的长度
     *
     * @param key 键
     * @return 元素个数
     */
    public long sLength(String key) {
        try {
            Long size = redisTemplate.opsForSet().size(key);
            if (size != null)
                return size;
            else
                return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 从key中移除jsonString
     *
     * @param key        key
     * @param jsonString json字符串
     * @return 是否操作成功
     */
    public boolean sRemove(String key, String jsonString) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, jsonString);
            if (count != null) {
                return count != 0;
            } else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Set<String> getAllKeys(){
        return redisTemplate.keys("*");
    }
}
