package com.example.bootdemo.service;

import com.example.bootdemo.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户服务测试类
 */
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testSave() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("123456");
        user.setEmail("test@example.com");
        user.setPhone("13800138000");
        user.setStatus(1);

        boolean result = userService.save(user);
        assertTrue(result);
        assertNotNull(user.getId());
        System.out.println("新增用户ID: " + user.getId());
    }

    @Test
    void testGetById() {
        // 先保存一个用户
        User user = new User();
        user.setUsername("getuser");
        user.setPassword("123456");
        userService.save(user);

        // 查询
        User found = userService.getById(user.getId());
        assertNotNull(found);
        assertEquals("getuser", found.getUsername());
    }

    @Test
    void testUpdateById() {
        // 先保存一个用户
        User user = new User();
        user.setUsername("updateuser");
        user.setPassword("123456");
        userService.save(user);

        // 更新
        user.setEmail("updated@example.com");
        boolean result = userService.updateById(user);
        assertTrue(result);

        // 验证
        User updated = userService.getById(user.getId());
        assertEquals("updated@example.com", updated.getEmail());
    }

    @Test
    void testRemoveById() {
        // 先保存一个用户
        User user = new User();
        user.setUsername("deleteuser");
        user.setPassword("123456");
        userService.save(user);
        Long id = user.getId();

        // 删除
        boolean result = userService.removeById(id);
        assertTrue(result);

        // 验证
        User deleted = userService.getById(id);
        assertNull(deleted);
    }

    @Test
    void testList() {
        // 先保存几个用户
        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setUsername("listuser" + i);
            user.setPassword("123456");
            userService.save(user);
        }

        List<User> list = userService.list();
        assertNotNull(list);
        assertTrue(list.size() >= 3);
        System.out.println("用户列表数量: " + list.size());
    }

    @Test
    void testGetByUsername() {
        // 先保存一个用户
        User user = new User();
        user.setUsername("uniqueuser");
        user.setPassword("123456");
        userService.save(user);

        // 根据用户名查询
        User found = userService.getByUsername("uniqueuser");
        assertNotNull(found);
        assertEquals("uniqueuser", found.getUsername());
    }

    @Test
    void testGetByEmail() {
        // 先保存一个用户
        User user = new User();
        user.setUsername("emailuser");
        user.setPassword("123456");
        user.setEmail("testemail@example.com");
        userService.save(user);

        // 根据邮箱查询
        User found = userService.getByEmail("testemail@example.com");
        assertNotNull(found);
        assertEquals("emailuser", found.getUsername());
    }
}
