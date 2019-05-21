package cn.tycoding.auth.feign.service.fallback;

import cn.tycoding.auth.feign.service.AuthFeignService;
import org.springframework.stereotype.Component;

/**
 * @author tycoding
 * @date 2019-05-19
 */
@Component
public class AuthFeignServiceFallbackImpl implements AuthFeignService {

    @Override
    public String hello(String name) {
        return "hello " + name + ", this is template-auth, but request error";
    }
}
