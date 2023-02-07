package com.msb.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * description :
 *
 * @author zhoujian
 * date :  2023-02-06 18:09
 */
@Component
public class RedisSessionDAO extends AbstractSessionDAO {

    @Autowired
    private RedisTemplate redisTemplate;

    private final String SHIRO_SESSION="session:";

    @Override
    protected Serializable doCreate(Session session) {

        // 1.基于session生成一个sessionId(唯一标识)
        Serializable sessionId = generateSessionId(session);

        // 2.将session和sessionId绑定到一起(可以基于session拿到sessionId)
        assignSessionId(session, sessionId);

        // 3.将sessionId作为key session作为value 存储session
        redisTemplate.opsForValue().set(SHIRO_SESSION+sessionId, session, 30, TimeUnit.MINUTES); // 30分钟
        System.out.println("redis ====== doCreate " + SHIRO_SESSION+sessionId);
        // 4.返回sessionId
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        // 1.基于sessionId获取session
        if (null == sessionId) {
            return null;
        }
        Session session = (Session) redisTemplate.opsForValue().get(SHIRO_SESSION + sessionId);
        System.out.println("redis ====== doReadSession " + SHIRO_SESSION+sessionId);
        if (null != session) {
            redisTemplate.expire(SHIRO_SESSION+sessionId, 30, TimeUnit.MINUTES);
        }
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (null == session) {
            return;
        }
        redisTemplate.opsForValue().set(SHIRO_SESSION+session.getId(), session, 30, TimeUnit.MINUTES);

        System.out.println("redis ====== update " + SHIRO_SESSION+session.getId());
    }

    @Override
    public void delete(Session session) {
        if (null == session) {
            return;
        }
        redisTemplate.delete(SHIRO_SESSION+session.getId());

        System.out.println("redis ====== delete " + SHIRO_SESSION+session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set keys = redisTemplate.keys(SHIRO_SESSION + "*");
        Set<Session> sessionSet = new HashSet<>();
        for (Object key : keys) {
            Session session = (Session) redisTemplate.opsForValue().get(key);
            sessionSet.add(session);
        }
        return sessionSet;
    }

}
