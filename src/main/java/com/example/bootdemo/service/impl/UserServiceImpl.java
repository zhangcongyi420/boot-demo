package com.example.bootdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bootdemo.common.exception.BusinessException;
import com.example.bootdemo.dao.UserDao;
import com.example.bootdemo.domain.User;
import com.example.bootdemo.domain.dto.UserDTO;
import com.example.bootdemo.domain.dto.UserUpdateDTO;
import com.example.bootdemo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createUser(UserDTO userDTO) {
        // 检查用户名是否已存在
        if (isUsernameExists(userDTO.getUsername())) {
            throw new BusinessException("用户名已存在，请更换其他用户名");
        }

        // 检查邮箱是否已被使用
        if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()) {
            User existUser = getByEmail(userDTO.getEmail());
            if (existUser != null) {
                throw new BusinessException("邮箱已被注册，请使用其他邮箱");
            }
        }

        // 创建用户
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setStatus(userDTO.getStatus() != null ? userDTO.getStatus() : 1);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        boolean success = save(user);
        if (!success) {
            throw new BusinessException("用户创建失败，请稍后重试");
        }

        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User updateUser(UserUpdateDTO userDTO) {
        // 检查用户是否存在
        User existUser = getById(userDTO.getId());
        if (existUser == null) {
            throw new BusinessException("用户不存在");
        }

        // 如果修改了用户名，检查新用户名是否已存在
        if (userDTO.getUsername() != null && !userDTO.getUsername().equals(existUser.getUsername())) {
            if (isUsernameExists(userDTO.getUsername(), userDTO.getId())) {
                throw new BusinessException("用户名已存在，请更换其他用户名");
            }
        }

        // 如果修改了邮箱，检查新邮箱是否已被使用
        if (userDTO.getEmail() != null && !userDTO.getEmail().equals(existUser.getEmail())) {
            User emailUser = getByEmail(userDTO.getEmail());
            if (emailUser != null && !emailUser.getId().equals(userDTO.getId())) {
                throw new BusinessException("邮箱已被注册，请使用其他邮箱");
            }
        }

        // 更新用户
        BeanUtils.copyProperties(userDTO, existUser);
        existUser.setUpdateTime(LocalDateTime.now());

        boolean success = updateById(existUser);
        if (!success) {
            throw new BusinessException("用户更新失败，请稍后重试");
        }

        return existUser;
    }

    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return getOne(wrapper);
    }

    @Override
    public User getByEmail(String email) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);
        return getOne(wrapper);
    }

    @Override
    public boolean isUsernameExists(String username) {
        return getByUsername(username) != null;
    }

    @Override
    public boolean isUsernameExists(String username, Long excludeId) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username)
               .ne(User::getId, excludeId);
        return getOne(wrapper) != null;
    }
}
