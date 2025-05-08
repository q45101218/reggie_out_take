package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishDto;

import java.util.List;

public interface DishService extends IService<Dish> {
    public void save(DishDto dishDto);
    public List<DishDto> listDishWithFlavor(Long categoryId);
}
