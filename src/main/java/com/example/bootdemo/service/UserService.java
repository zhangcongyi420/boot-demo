package com.example.bootdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bootdemo.domain.User;
import com.example.bootdemo.domain.dto.UserDTO;
import com.example.bootdemo.domain.dto.UserUpdateDTO;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {

    /**
     * 新增用户（带校验）
     */
    User createUser(UserDTO userDTO);

    /**
     * 更新用户（带校验）
     */
    User updateUser(UserUpdateDTO userDTO);

    /**
     * 根据用户名查询用户
     */
    User getByUsername(String username);

    /**
     * 根据邮箱查询用户
     */
    User getByEmail(String email);

    /**
     * 检查用户名是否存在
     */
    boolean isUsernameExists(String username);

    /**
     * 检查用户名是否存在（排除指定ID）
     */
    boolean isUsernameExists(String username, Long excludeId);
}
