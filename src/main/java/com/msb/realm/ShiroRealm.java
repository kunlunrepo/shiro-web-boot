package com.msb.realm;

import com.alibaba.druid.util.StringUtils;
import com.msb.entity.Permission;
import com.msb.entity.Role;
import com.msb.entity.User;
import com.msb.service.PermissionService;
import com.msb.service.RoleService;
import com.msb.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-02-02 13:52
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(1024);
        this.setCredentialsMatcher(matcher);
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 1.获取用户名
        String username = (String) token.getPrincipal();

        // 2.判断用户名
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        // 3.基于用户名查询用户信息
        User user = userService.findByUsername(username);

        // 4.user对象是否为null
        if (null == user) {
            return null;
        }

        // 5.声明AuthenticationInfo ，并填充用户信息
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),"CustomRealm");
        info.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));

        // 6.
        return info;
    }

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 0.判断是否认证
        Subject subject = SecurityUtils.getSubject();
        if (subject == null) {
            return null;
        }
        if (!subject.isAuthenticated()) {
            return null;
        }

        // 1.获取认证用户信息
        User user = (User) principals.getPrimaryPrincipal();

        // 2.查询用户的角色
        Set<Role> roleSet = roleService.findRoleByUid(user.getId());
        Set<Integer> roleIdSet = new HashSet<>();
        Set<String> roleNameSet = new HashSet<>();
        for (Role role : roleSet) {
            roleIdSet.add(role.getId());
            roleNameSet.add(role.getRoleName());
        }

        // 3.查询角色的权限
        Set<Permission> permSet = permissionService.findPermsByRoleSet(roleIdSet);
        Set<String> permNameSet = new HashSet<>();
        for (Permission permission : permSet) {
            permNameSet.add(permission.getPermName());
        }

        // 4.声明AuthorizationInfo，传入角色和权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roleNameSet);
        info.setStringPermissions(permNameSet);

        // 5.返回
        return info;
    }

}
