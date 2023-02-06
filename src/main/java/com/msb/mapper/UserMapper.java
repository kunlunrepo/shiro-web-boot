package com.msb.mapper;

import com.msb.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-02-02 17:35
 */
public interface UserMapper {

    @Select("select * from tb_user where username = #{username}")
     User findUserByUsername(@Param("username")String username);
}
