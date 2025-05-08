package com.itheima.reggie.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService extends IService<ShoppingCart> {
    public void addCart(ShoppingCart shoppingCart);
    public List<ShoppingCart> listCart(Long uid);
}
