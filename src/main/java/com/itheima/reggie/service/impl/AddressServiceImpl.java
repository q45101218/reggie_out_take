package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.entity.AddressBook;
import com.itheima.reggie.mapper.AddressBookMapper;
import com.itheima.reggie.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressService{

    @Autowired
    private AddressBookMapper addressBookMapper;
    @Override
    public List<AddressBook> listOwnAdd() {
        return query().eq("user_id", BaseContext.getEId()).list();
    }

    @Override
    public void setDefault(Long id) {
        addressBookMapper.clearDefault(BaseContext.getEId());
        addressBookMapper.setDefault(id);
    }

    @Override
    public void updateAdd(AddressBook addressBook) {
        addressBookMapper.updateAdd(addressBook);
    }
}
