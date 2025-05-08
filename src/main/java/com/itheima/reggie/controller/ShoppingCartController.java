package com.itheima.reggie.controller;


import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.ShoppingCart;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;
    @PostMapping ("/add")
    public R<String> addCart(@RequestBody ShoppingCart shoppingCart, HttpSession session){
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCart.setUserId((Long)((User)session.getAttribute("user")).getId());
        shoppingCartService.addCart(shoppingCart);
        return R.success("chenggong");
    }

    @GetMapping("/list")
    public R<List<ShoppingCart>> listCart(HttpSession session){

        return R.success(shoppingCartService.listCart((Long)((User)session.getAttribute("user")).getId()));
    }
}
