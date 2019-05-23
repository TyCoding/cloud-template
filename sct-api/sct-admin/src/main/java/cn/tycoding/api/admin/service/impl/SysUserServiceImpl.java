package cn.tycoding.api.admin.service.impl;

import cn.tycoding.api.admin.entity.SysUser;
import cn.tycoding.api.admin.mapper.SysUserMapper;
import cn.tycoding.api.admin.service.SysUserService;
import cn.tycoding.api.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-05-22
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findByName(String username) {
        Example example = new Example(SysUser.class);
        example.createCriteria().andCondition("username=", username);
        List<SysUser> list = sysUserMapper.selectByExample(example);
        return list.size() > 0 ? list.get(0) : null;
    }
}
