package com.ywl.service.impl;

import com.ywl.common.exception.ApplicationException;
import com.ywl.common.response.R;
import com.ywl.common.response.RespCodeEnum;
import com.ywl.dao.UserMapper;
import com.ywl.entity.User;
import com.ywl.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * 用户表(User)表服务实现类
 *
 * @author yangwulin
 * @since 2020-05-14 09:31:37
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Integer id) {
        return userMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<User> queryAllByLimit(int offset, int limit) {
        return userMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 通过用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public List<User> queryUserByUserName(String username) {
        return userMapper.selectByUserName(username);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return R 响应模型对象
     */
    @Override
    public R insert(User user) {
        // 利用uuid生成随机salt
        String salt = UUID.randomUUID().toString();
        // 密码加盐 然后进行md5加密
        String password = DigestUtils.md5DigestAsHex((user.getPassword() + salt).getBytes());
        // 设置密码
        user.setPassword(password);
        // 设置salt
        user.setSalt(salt);

        List<User> userList = userMapper.selectByUserName(user.getUsername());

        if (userList != null && !userList.isEmpty()) {
            throw new ApplicationException(RespCodeEnum.USER_NAME_EXIST);
        }

        // 插入数据到数据库
        int insert = userMapper.insert(user);

        return insert == 1 ? R.createSuccess("注册成功") : R.createError("注册失败");
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User update(User user) {
        this.userMapper.update(user);
        return queryById(user.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return userMapper.deleteById(id) > 0;
    }
}