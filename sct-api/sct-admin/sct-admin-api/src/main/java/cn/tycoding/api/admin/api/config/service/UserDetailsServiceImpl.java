package cn.tycoding.api.admin.api.config.service;

import cn.tycoding.api.admin.api.config.SctUser;
import cn.tycoding.api.admin.api.entity.SysUser;
import cn.tycoding.api.admin.api.feign.RemoteUserService;
import cn.tycoding.api.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 加载用户数据
 *
 * @author tycoding
 * @date 2019-05-24
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RemoteUserService remoteUserService;

    /**
     * 加载用户信息
     *
     * @param username 传递username值
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername >> username = {}", username);
        Result<SysUser> result = remoteUserService.info(username);
        return getUserDetails(result);
    }

    /**
     * 构造包含用户信息的UserDetails对象。本项目仅提供用户信息，其他数据模拟
     * 应该包括：用户信息、角色信息、权限信息，这些数据都应该从数据库中查询。
     *
     * @param result
     * @return
     */
    private UserDetails getUserDetails(Result<SysUser> result) {
        if (result == null || result.getData() == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        SysUser user = result.getData();

        // 模拟构造包含用户角色列表的`List<GrantedAuthority>`对象
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList("ADMIN");

        return new SctUser(user.getId(), user.getUsername(), user.getPassword(), true, true, true, true, authorityList);
    }
}
