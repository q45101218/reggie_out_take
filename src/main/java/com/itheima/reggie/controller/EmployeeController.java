package com.itheima.reggie.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.ActiveDTO;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        System.out.println("user:" + employee);
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println("password"+password);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        if(emp == null)
        {
            return R.error("登陆失败1。");
        } else if (!emp.getPassword().equals(password))
        {
            return R.error("登陆失败2。");
        } else if (emp.getStatus().equals(0))
        {
            return R.error("员工账户已禁用。");
        }
        request.getSession().setAttribute("emp", emp);
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request)
    {
        request.getSession().removeAttribute("emp");
        return R.success("success");
    }

    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee emp)
    {
        emp.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

//        emp.setCreateTime(LocalDateTime.now());
//        emp.setUpdateTime(LocalDateTime.now());
//        emp.setCreateUser((Long)((Employee)request.getSession().getAttribute("emp")).getId());
//        emp.setUpdateUser((Long)((Employee)request.getSession().getAttribute("emp")).getId());
        log.info("emp information {}", emp.toString());
        return employeeService.save(emp) ? R.success("chenggong") : R.error("失败");
    }

//    @GetMapping("/page")
//    public R<List> empList(){
//        return null;
//    }
    @GetMapping("/page")
    public R<Page<Employee>> page(int page, int pageSize,String name){
        log.info("page = {}, pagesize = {}, name = {}", page, pageSize, name);
        Page pageInfo = new Page(page, pageSize);

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(!StringUtils.isEmpty(name), Employee::getName, name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    @PutMapping()
    public R<String> active(HttpServletRequest request,@RequestBody Employee emp){
//        emp.setUpdateTime(LocalDateTime.now());
//        emp.setUpdateUser((Long)((Employee)request.getSession().getAttribute("emp")).getId());
        employeeService.updateById(emp);
        return R.success("成功");
    }

    @GetMapping("/{id}")
    public R<Employee> update(@PathVariable Long id){
        Employee employee = new Employee();
        employee.setId(id);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Employee::getId, employee.getId());

        employee = employeeService.getOne(queryWrapper);
        return R.success(employee);
    }

}
