package cn.tycoding.api.auth.controller;

import cn.tycoding.api.admin.entity.SysUser;
import cn.tycoding.api.admin.feign.RemoteUserService;
import cn.tycoding.api.common.constant.enums.CommonEnums;
import cn.tycoding.api.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tycoding
 * @date 2019-05-21
 */
@Slf4j
@RestController
public class AuthController {

    @Autowired
    private RemoteUserService remoteUserService;

    @PostMapping("/user/login")
    public Result<SysUser> info(@RequestBody SysUser sysUser) {
        Result<SysUser> info = remoteUserService.info(sysUser.getUsername());
        SysUser user = info.getData();
        if (user != null && user.getPassword().equals(sysUser.getPassword())) {
            info.getData().setToken("xxxxxxxxxxxx");
            return info;
        } else {
            log.error("Login Error, user >>> {}", sysUser);
            return new Result<>(CommonEnums.LOGIN_ERROR);
        }
    }

    @GetMapping("/user/info")
    public Result<SysUser> info() {
        return remoteUserService.info("tycoding");
    }

    @GetMapping("/user/logout")
    public Result logout() {
        return new Result();
    }
}
