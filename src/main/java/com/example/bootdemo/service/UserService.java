package com.example.bootdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bootdemo.domain.User;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名查询用户
     */
    User getByUsername(String username);

    /**
     * 根据邮箱查询用户
     */
    User getByEmail(String email);
}
