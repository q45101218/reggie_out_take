package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishDto;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Resource
    private DishFlavorService dishFlavorService;
    @Resource
    private DishMapper dishMapper;

    @Transactional
    public void save(DishDto dishDto)
    {
        this.save((Dish)dishDto);
        for (DishFlavor df : dishDto.getFlavors()){
            df.setDishId(dishDto.getId());
        }

        System.out.println(dishDto.getFlavors());
        dishFlavorService.saveBatch(dishDto.getFlavors());
    }

    @Transactional
    @Override
    public boolean removeById(Serializable id) {
        super.removeById(id);
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,id);
        dishFlavorService.remove(dishFlavorLambdaQueryWrapper);
        return true;
    }

    @Override
    public List<DishDto> listDishWithFlavor(Long categoryId){
        //System.out.println("12345");
        return dishMapper.listDishWithFlavor(categoryId);
    }
}
