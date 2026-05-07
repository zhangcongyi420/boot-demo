package com.example.bootdemo.controller;

import com.example.bootdemo.common.Result;
import com.example.bootdemo.domain.User;
import com.example.bootdemo.service.UserService;
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
    public Result<Boolean> save(@RequestBody User user) {
        boolean success = userService.save(user);
        return success ? Result.success(true) : Result.error("新增失败");
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
    public Result<Boolean> updateById(@RequestBody User user) {
        boolean success = userService.updateById(user);
        return success ? Result.success(true) : Result.error("更新失败");
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
}
