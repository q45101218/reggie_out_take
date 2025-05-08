package com.itheima.reggie.controller;


import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.impl.CategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;
    @GetMapping("/page")
    public R<Page<Category>> listAll(int page, int pageSize){
        Page pageInfo = new Page(page, pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.orderByDesc(Category::getSort);
        categoryService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    @PostMapping()
    public R<String> add(@RequestBody Category category){
        return categoryService.save(category)? R.success("chenggong") : R.error("fail");
    }

    @DeleteMapping
    public R<String> delete(@RequestParam Long ids)
    {
        return categoryService.removeById(ids)? R.success("chenggong") : R.error("fail");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category)
    {
        return categoryService.updateById(category)? R.success("chenggong") : R.error("fail");
    }

    @GetMapping("/list")
    public R<List> listByType(@RequestParam(value = "type", required = false) Integer type){
        System.out.println("requestParam:" + type);
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(type != null,Category::getType, type);
        return R.success(categoryService.list(lambdaQueryWrapper));
    }
}
