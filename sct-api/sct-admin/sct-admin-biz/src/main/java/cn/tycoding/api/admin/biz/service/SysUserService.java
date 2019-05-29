package cn.tycoding.api.admin.biz.service;

import cn.tycoding.api.admin.api.entity.SysUser;
import cn.tycoding.api.common.service.BaseService;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-05-22
 */
public interface SysUserService extends BaseService<SysUser> {

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    SysUser findByName(String username);

    /**
     * 条件查询用户
     *
     * @param user
     * @return
     */
    List<SysUser> list(SysUser user);

    /**
     * 新增用户
     *
     * @param user
     */
    void create(SysUser user);

    /**
     * 更新用户
     *
     * @param user
     */
    void update(SysUser user);

    /**
     * 修改密码
     *
     * @param user
     */
    void changePass(SysUser user);
}
