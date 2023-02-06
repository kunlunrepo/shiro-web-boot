package com.msb.service;

import com.msb.entity.Permission;

import java.util.Set;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-02-03 11:47
 */
public interface PermissionService {

    Set<Permission> findPermsByRoleSet(Set<Integer> roleIdSet);
}
