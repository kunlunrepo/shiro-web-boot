package com.msb.service;

import com.msb.entity.User;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-02-02 17:38
 */
public interface UserService {

    User findByUsername(String username);
}
