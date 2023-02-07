package com.msb.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-02-07 11:13
 */
@Component
public class RedisCache<K,V> implements Cache<K,V> {

    @Autowired
    private RedisTemplate redisTemplate;

    private final String CACHE_PREFIX = "cache:";
    
    @Override
    public V get(K k) throws CacheException {
        System.out.println("从redis查询授权信息 "+k);
        V v = (V) redisTemplate.opsForValue().get(CACHE_PREFIX + k);
        if (null != v) {
            redisTemplate.expire(CACHE_PREFIX+k, 15, TimeUnit.MINUTES);
        }
        return v;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        System.out.println("向redis设置授权信息 "+k);
        redisTemplate.opsForValue().set(CACHE_PREFIX + k, v, 15, TimeUnit.MINUTES);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        System.out.println("删除redis授权信息 ");
        V v = (V) redisTemplate.opsForValue().get(CACHE_PREFIX + k);
        if (null != v) {
            redisTemplate.delete(CACHE_PREFIX + k);
        }
        return v;
    }

    @Override
    public void clear() throws CacheException {
        System.out.println("全部删除redis授权信息 ");
        Set keys = redisTemplate.keys(CACHE_PREFIX + "*");
        redisTemplate.delete(keys);

    }

    @Override
    public int size() {
        Set keys = redisTemplate.keys(CACHE_PREFIX + "*");
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        Set keys = redisTemplate.keys(CACHE_PREFIX + "*");
        return keys;
    }

    @Override
    public Collection<V> values() {
        System.out.println("获取全部redis授权信息 ");
        Set values = new HashSet();
        Set keys = redisTemplate.keys(CACHE_PREFIX + "*");
        for (Object key : keys) {
            Object o = redisTemplate.opsForValue().get(key);
            values.add(o);
        }
        return values;
    }
    
}
