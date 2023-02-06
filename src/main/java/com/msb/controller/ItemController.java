package com.msb.controller;

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
}
