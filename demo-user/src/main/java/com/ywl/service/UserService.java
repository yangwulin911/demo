package com.ywl.service;

import com.ywl.common.response.R;
import com.ywl.entity.User;
import java.util.List;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2020-05-14 09:31:37
 */
public interface UserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<User> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    R insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User update(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 通过用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    List<User> queryUserByUserName(String username);
}