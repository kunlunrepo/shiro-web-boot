package com.msb.mapper;

import com.msb.entity.Role;
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
public interface RoleMapper {

    @Select("select r.* from tb_role r, user_role ur where r.id= ur.rid and ur.uid = #{uid}")
    Set<Role> findRoleByUid(@Param("uid")Integer uid);
}
