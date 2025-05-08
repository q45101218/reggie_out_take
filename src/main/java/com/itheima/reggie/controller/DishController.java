package com.itheima.reggie.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishDto;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/dish")
public class DishController {
    @Resource
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    @Transactional
    public R<Page> listAll(@RequestParam("page") int page,
                           @RequestParam("pageSize") int size,
                           @RequestParam(value="name", required = false) String search){
        Page<Dish> p = new Page<>(page, size);
        Page<DishDto> p1 = new Page<>();
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.like(!StringUtils.isEmpty(search), Dish::getName, search);
        dishLambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(p, dishLambdaQueryWrapper);
        BeanUtils.copyProperties(p, p1, "records");
        List<DishDto> dishDtoList = new ArrayList<>();
        for(Dish dd : p.getRecords()){
            DishDto d = new DishDto();
            BeanUtils.copyProperties(dd, d);
            d.setCategoryName(categoryService.getById(dd.getCategoryId()).getName());
            dishDtoList.add(d);
        }
        p1.setRecords(dishDtoList);

        return R.success(p1);
    }

    @PostMapping
    public R<String> add(@RequestBody DishDto dishDto){
        System.out.println(dishDto);
        //dishFlavorService.save()
        dishService.save(dishDto);
        return R.success("cg");
    }

    @GetMapping("/{id}")
    public R<DishDto> getdata(@PathVariable Long id){
        DishDto dishDto = new DishDto();
        //Dish dish = new Dish();
        BeanUtils.copyProperties(dishService.getById(id), dishDto);
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, id);

        dishDto.setFlavors(dishFlavorService.list(dishFlavorLambdaQueryWrapper));
        return R.success(dishDto);
    }

    @PutMapping
    @Transactional
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.removeById(dishDto.getId());
        dishService.save(dishDto);
        return R.success("cs");
    }

    @DeleteMapping
    public R<String> delete(@RequestParam Long ids){
        dishService.removeById(ids);
        return R.success("cs");
    }

    @GetMapping("/list")
    public R<List> type(@RequestParam Long categoryId){
        return R.success(dishService.listDishWithFlavor(categoryId));
//        LambdaQueryWrapper<Dish> dishDtoLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        dishDtoLambdaQueryWrapper.eq(Dish::getCategoryId, categoryId);

        //return R.success(dishService.list(dishDtoLambdaQueryWrapper));
    }

}
