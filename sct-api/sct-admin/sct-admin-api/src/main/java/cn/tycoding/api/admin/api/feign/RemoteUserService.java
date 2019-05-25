package cn.tycoding.api.admin.api.feign;

import cn.tycoding.api.admin.api.entity.SysUser;
import cn.tycoding.api.admin.api.feign.fallback.RemoteUserServiceFallbackImpl;
import cn.tycoding.api.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户远程调用接口
 *
 * @author tycoding
 * @date 2019-05-22
 */
@FeignClient(value = "sct-admin-biz", fallback = RemoteUserServiceFallbackImpl.class)
public interface RemoteUserService {

    /**
     * 根据用户名查找用户信息
     *
     * @return
     */
    @GetMapping("/user/info/{username}")
    Result<SysUser> info(@PathVariable("username") String username);
}
