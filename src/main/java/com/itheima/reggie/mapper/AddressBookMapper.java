package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {

    void setDefault(@Param("id") Long id);

    void clearDefault(@Param("uid") Long id);

    void updateAdd(AddressBook addressBook);
}
