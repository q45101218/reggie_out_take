package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.service.UserService;
import com.itheima.reggie.utils.ValidateCodeUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/sendMsg")
    @ApiOperation("手机验证码")
    public R<String> getCode(@RequestBody User user, HttpSession session){
        if(StringUtils.isEmpty(user.getPhone())){
            return R.error("手机号不为空");
        }

        String code = ValidateCodeUtils.generateValidateCode(4).toString();
        session.setAttribute("code",code);

        return R.success(code);
    }

    @PostMapping("/login")
    @ApiOperation("用户登陆")
    public R<String> login(@RequestBody Map map, HttpSession session){
        log.info("phone:{}, code:{}", map.get("phone"), map.get("code"));

        if(session.getAttribute("code").equals(map.get("code"))){

            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getPhone, map.get("phone"));
            User user = userService.getOne(userLambdaQueryWrapper);
            if(user ==  null)
            {
                user = new User();
                user.setPhone(map.get("phone").toString());
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user", user);
            return R.success("登陆成功");
        }
        return R.error("验证码错误");
    }
}
