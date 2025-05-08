package com.itheima.reggie.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.ShoppingCart;
import com.itheima.reggie.mapper.ShoppingCartMapper;
import com.itheima.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    @Autowired
    public ShoppingCartMapper shoppingCartMapper;

    @Override
    public void addCart(ShoppingCart shoppingCart) {
        ShoppingCart inCart = shoppingCartMapper.findInCart(shoppingCart.getUserId(), shoppingCart.getDishId());
        if(null != inCart)
        {
            inCart.setNumber(inCart.getNumber()+1);
            shoppingCartMapper.updateCart(inCart);
        }else {
            shoppingCartMapper.addCart(shoppingCart);
        }
    }

    @Override
    public List<ShoppingCart> listCart(Long uid) {
        //if(shoppingCartMapper.findInCart())
        return shoppingCartMapper.listCart(uid);
    }
}
