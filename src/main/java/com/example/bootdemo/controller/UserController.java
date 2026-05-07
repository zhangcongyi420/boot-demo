package com.example.bootdemo.controller;

import com.example.bootdemo.common.Result;
import com.example.bootdemo.domain.User;
import com.example.bootdemo.domain.dto.UserDTO;
import com.example.bootdemo.domain.dto.UserUpdateDTO;
import com.example.bootdemo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 新增用户
     */
    @PostMapping
    public Result<User> save(@Valid @RequestBody UserDTO userDTO) {
        User user = userService.createUser(userDTO);
        return Result.success(user);
    }

    /**
     * 根据ID删除用户
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> removeById(@PathVariable Long id) {
        boolean success = userService.removeById(id);
        return success ? Result.success(true) : Result.error("删除失败");
    }

    /**
     * 更新用户
     */
    @PutMapping
    public Result<User> updateById(@Valid @RequestBody UserUpdateDTO userDTO) {
        User user = userService.updateUser(userDTO);
        return Result.success(user);
    }

    /**
     * 根据ID查询用户
     */
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return user != null ? Result.success(user) : Result.error("用户不存在");
    }

    /**
     * 查询所有用户
     */
    @GetMapping("/list")
    public Result<List<User>> list() {
        List<User> list = userService.list();
        return Result.success(list);
    }

    /**
     * 根据用户名查询用户
     */
    @GetMapping("/username/{username}")
    public Result<User> getByUsername(@PathVariable String username) {
        User user = userService.getByUsername(username);
        return user != null ? Result.success(user) : Result.error("用户不存在");
    }

    /**
     * 检查用户名是否存在
     */
    @GetMapping("/check-username")
    public Result<Boolean> checkUsername(@RequestParam String username) {
        boolean exists = userService.isUsernameExists(username);
        return Result.success(exists);
    }
}
