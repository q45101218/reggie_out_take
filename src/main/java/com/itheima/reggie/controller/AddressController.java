package com.itheima.reggie.controller;

import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.AddressBook;
import com.itheima.reggie.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressController {

    @Resource
    private AddressService addressService;

    @GetMapping("/list")
    public R<List> listAddress()
    {
        return R.success(addressService.listOwnAdd());
    }

    @PostMapping
    public R<String> addAddress(@RequestBody AddressBook addressBook)
    {
        addressBook.setUserId(BaseContext.getEId());
        addressService.save(addressBook);
        return R.success("成功");
    }

    @PutMapping("/default")
    public R<String> setDefault(@RequestBody AddressBook addressBook)
    {
        addressService.setDefault(addressBook.getId());
        return R.success("12");
    }

    @GetMapping("/{id}")
    public R<AddressBook> getAdd(@PathVariable("id") Long id){
        return R.success(addressService.getById(id));
    }

    @PutMapping
    public R<String> updateAdd(@RequestBody AddressBook addressBook)
    {
        addressService.updateAdd(addressBook);
        return R.success("123");
    }
}
