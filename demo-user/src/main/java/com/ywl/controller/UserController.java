package com.ywl.controller;

import com.ywl.common.response.R;
import com.ywl.dto.RegisterDto;
import com.ywl.entity.User;
import com.ywl.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 用户表(User)表控制层
 *
 * @author makejava
 * @since 2020-05-14 09:31:38
 */
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne/{id}")
    public User selectOne(@PathVariable Integer id) {
        return this.userService.queryById(id);
    }

    /**
     * 通过用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    @PostMapping("/selectUser")
    public List<User> SelectUserByUsername(String username) {
        return userService.queryUserByUserName(username);
    }

    /**
     * 用户注册
     * @param dto 用户注册dto
     * @return 注册是否成功（true：成功；false：失败）
     */
    @PostMapping("/register")
    public R register(@RequestBody @Valid RegisterDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());

        return userService.insert(user);
    }

}