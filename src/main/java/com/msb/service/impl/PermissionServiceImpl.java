package com.msb.service.impl;

import com.msb.entity.Permission;
import com.msb.mapper.PermissionMapper;
import com.msb.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-02-03 11:47
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Set<Permission> findPermsByRoleSet(Set<Integer> roleIdSet) {

        return permissionMapper.findPermsByRoleIdIn(roleIdSet);
    }
}
