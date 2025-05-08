package com.itheima.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.common.R;

import com.itheima.reggie.entity.SetmealDto;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.entity.SetmealDish;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.SetmealDishService;
import com.itheima.reggie.service.SetmealService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/setmeal")
@RestController
public class SetMealController {
    @Resource
    private SetmealService setmealService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private SetmealDishService setMealDishService;

    @GetMapping("/page")
    public R<Page<SetmealDto>> getList(@RequestParam("page") int page,
                                    @RequestParam("pageSize") int size,
                                    @RequestParam(value = "name", required = false) String name){
        Page<Setmeal> p = new Page<>(page, size);
        Page<SetmealDto> p1 = new Page<>();
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Setmeal::getName,name);
        setmealService.page(p, setmealLambdaQueryWrapper);
        BeanUtils.copyProperties(p, p1,"records");
        List<SetmealDto> smd = new ArrayList<>();
        for(Setmeal sm : p.getRecords()){
            SetmealDto tmp = new SetmealDto();
            BeanUtils.copyProperties(sm, tmp);
            tmp.setCategoryName(categoryService.getById(tmp.getCategoryId()).getName());
            smd.add(tmp);
        }
        p1.setRecords(smd);
        //p1.getRecords().add(new SetMealDto());
        return R.success(p1);
    }


    @PostMapping
    @Transactional
    public R<String> add(@RequestBody SetmealDto setMealDto){
        Setmeal sm = new Setmeal();
        BeanUtils.copyProperties(setMealDto, sm);
        setmealService.save(sm);

        for(SetmealDish smd: setMealDto.getSetmealDishes()){
            System.out.println("geId：" + sm.getId());
            smd.setSetmealId(sm.getId());
        }
        setMealDishService.saveBatch(setMealDto.getSetmealDishes());
        return R.success("cs");
    }

    @DeleteMapping()
    //@Transactional
    public R<String> delete(@RequestParam List<Long> ids){
        for(Long id: ids) {
            Setmeal ssm = setmealService.getById(id);
            System.out.println(ssm.getName());
            if(ssm.getStatus() == 1)
            {
                throw new CustomException("zhengzaishoumai");
            }
            setmealService.removeById(id);
            LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
            setmealDishLambdaQueryWrapper.eq(SetmealDish::getSetmealId, id);
            setMealDishService.remove(setmealDishLambdaQueryWrapper);
        }
        return R.success("cs");
    }

    @GetMapping("/list")
    public R<List> listSetmeal(@RequestParam Long categoryId, @RequestParam int status)
    {
        //SetmealService.listSetmeal(categoryId, status);
        return R.success(setmealService.listSetmeal(categoryId, status));
    }
}
