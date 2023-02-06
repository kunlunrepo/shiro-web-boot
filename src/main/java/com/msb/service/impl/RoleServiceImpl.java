package com.msb.service.impl;

import com.msb.entity.Role;
import com.msb.mapper.RoleMapper;
import com.msb.service.RoleService;
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
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public Set<Role> findRoleByUid(Integer uid) {
        return roleMapper.findRoleByUid(uid);
    }
}
