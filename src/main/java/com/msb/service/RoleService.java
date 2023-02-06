package com.msb.service;

import com.msb.entity.Role;

import java.util.Set;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-02-03 11:47
 */
public interface RoleService {

    Set<Role> findRoleByUid(Integer uid);
}
