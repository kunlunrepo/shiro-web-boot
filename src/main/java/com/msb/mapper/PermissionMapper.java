package com.msb.mapper;

import com.msb.entity.Permission;
import com.msb.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-02-02 17:35
 */
public interface PermissionMapper {

    Set<Permission> findPermsByRoleIdIn(@Param("roleIdSet") Set<Integer> roleIdSet);
}
