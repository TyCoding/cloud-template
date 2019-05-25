package cn.tycoding.api.admin.api.feign.fallback;

import cn.tycoding.api.admin.api.entity.SysUser;
import cn.tycoding.api.admin.api.feign.RemoteUserService;
import cn.tycoding.api.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author tycoding
 * @date 2019-05-22
 */
@Slf4j
@Component
public class RemoteUserServiceFallbackImpl implements RemoteUserService {

    @Override
    public Result<SysUser> info(String username) {
        log.error("查询用户信息失败，username >> {}", username);
        return null;
    }
}
