package com.itheima.reggie.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

    public void addCart(ShoppingCart shoppingCart);
    public void eraseCart();
    public List<ShoppingCart> listCart(@Param("uid") Long uid);
    public ShoppingCart findInCart(@Param("uid") Long uid, @Param("did") Long did);
    public void updateCart(ShoppingCart shoppingCart);
}
