package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.AddressBook;

import java.util.List;


public interface AddressService extends IService<AddressBook> {
    List<AddressBook> listOwnAdd();

    void setDefault(Long id);

    void updateAdd(AddressBook addressBook);
}
