package com.example.bootdemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bootdemo.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据访问层
 */
@Mapper
public interface UserDao extends BaseMapper<User> {
}
