package com.msb.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Set;

/**
 * description : 仅有一个权限时
 *
 * @author kunlunrepo
 * date :  2023-02-03 17:40
 */
public class RolesOrAuthorizationFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        // 获取主体subject
        Subject subject = getSubject(request, response);

        // 将传入的角色转成数组操作
        String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }

        for (String role : rolesArray) {
            if (subject.hasRole(role)) {
                return true;
            }
        }
        return false;
    }

}
