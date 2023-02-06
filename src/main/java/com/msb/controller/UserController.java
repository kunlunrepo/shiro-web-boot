package com.msb.controller;

import com.msb.entity.User;
import com.msb.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-02-02 17:40
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(String username, String password) {
        // 1.直接获取subject主体 不需要像shiro-simple手动整合
        Subject subject = SecurityUtils.getSubject();

        // 2.发起认证
        try {
            subject.login(new UsernamePasswordToken(username, password));
            return "login success";
        } catch (Exception e) {
            e.printStackTrace();
            return "login fail";
        }
    }

    @GetMapping("test")
    public User test(String username) {
        return userService.findByUsername(username);
    }
}
