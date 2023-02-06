package com.msb.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-02-03 14:54
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    @GetMapping("/select")
    public String select() {
        return "item select";
    }

    @GetMapping("/delete")
    public String delete() {
        return "item delete";
    }

    @GetMapping("/update")
    @RequiresRoles(value = {"超级管理员", "运营"})
    public String update() {
        return "item update";
    }

    @GetMapping("/insert")
    @RequiresRoles(value = {"超级管理员", "运营"}, logical = Logical.OR)
    public String insert() {
        return "item insert";
    }

    @GetMapping("/rememberMe")
    public String rememberMe() {
        return "item rememberMe";
    }

    @GetMapping("/authentication")
    public String authentication() {
        return "item authentication";
    }
}
