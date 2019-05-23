package cn.tycoding.api.admin.service;

import cn.tycoding.api.admin.entity.SysUser;
import cn.tycoding.api.common.service.BaseService;

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
}
