package com.msb.config;

import com.msb.filter.RolesOrAuthorizationFilter;
import com.msb.realm.ShiroRealm;
import com.msb.session.DefaultRedisSessionManager;
import com.msb.session.RedisSessionDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-02-03 14:39
 */
@Configuration
public class ShiroConfig {

    @Bean
    protected SessionManager sessionManager(RedisSessionDAO redisSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultRedisSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(ShiroRealm realm, SessionManager sessionManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean
    public DefaultShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        Map<String, String> map = new LinkedHashMap<>();
        map.put("/login.html", "anon");
        map.put("user/logout", "logout");
        map.put("/user/**", "anon");
        map.put("/item/rememberMe", "user"); // 安全级别较低，采用user过滤器拦截 只要登录过，不需要重新登录就可以访问
        map.put("/item/authentication", "authc");
        map.put("/item/select", "rolesOr[超级管理员,运营]");
        map.put("/item/delete", "perms[item:delete,item:insert]");
        map.put("/**", "authc");
        definition.addPathDefinitions(map);
        return definition;
    }

    @Value("#{ @environment['shiro.loginUrl'] ?: '/login.jsp' }")
    protected String loginUrl;

    @Value("#{ @environment['shiro.successUrl'] ?: '/' }")
    protected String successUrl;

    @Value("#{ @environment['shiro.unauthorizedUrl'] ?: null }")
    protected String unauthorizedUrl;

    @Bean // 加了@Bean其参数都是从容器中获取的
    protected ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, ShiroFilterChainDefinition shiroFilterChainDefinition) {
        // 1.构建ShiroFilterFactoryBean工厂
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();

        // 2.设置路径
        filterFactoryBean.setLoginUrl(loginUrl);
        filterFactoryBean.setSuccessUrl(successUrl);
        filterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);

        // 3.设置安全管理器
        filterFactoryBean.setSecurityManager(securityManager);

        // 4.设置过滤器链
        filterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());

        // 5.设置自定义过滤器 必须手动new自定义过滤器，交给spring管理会造成获取不到subject
        filterFactoryBean.getFilters().put("rolesOr", new RolesOrAuthorizationFilter());

        // 6.返回工厂
        return filterFactoryBean;
    }
}
