package cn.tycoding.api.admin.biz.controller;

import cn.tycoding.api.admin.api.entity.SysUser;
import cn.tycoding.api.admin.api.utils.SecurityUtils;
import cn.tycoding.api.admin.biz.service.SysUserService;
import cn.tycoding.api.common.constant.enums.CommonEnums;
import cn.tycoding.api.common.controller.BaseController;
import cn.tycoding.api.common.utils.QueryPage;
import cn.tycoding.api.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author tycoding
 * @date 2019-05-22
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "SysUserController", tags = {"用户信息管理接口"})
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取当前用户信息
     *
     * @return
     */
    @GetMapping("/info")
    @ApiOperation(value = "获取当前授权用户信息", notes = "必须经过了OAuth授权")
    public Result<SysUser> info() {
        String username = SecurityUtils.getUsername();
        SysUser user = sysUserService.findByName(username);
        if (user == null) {
            return new Result<>(CommonEnums.USER_ERROR);
        }
        return new Result<>(user);
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    @GetMapping("/info/{username}")
    @ApiOperation(value = "根据用户名查询用户信息")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String")
    public Result<SysUser> info(@PathVariable("username") String username) {
        return new Result<SysUser>(sysUserService.findByName(username));
    }

    /**
     * 分页查询列表数据，条件查询
     *
     * @param user
     * @return
     */
    @PostMapping("/list")
    @ApiOperation(value = "分页、条件查询用户列表信息")
    @ApiImplicitParam(name = "user", value = "查询条件", required = true, dataType = "SysUser", paramType = "body")
    public Result<Map> list(SysUser user, QueryPage queryPage) {
        return new Result<Map>(this.selectByPageNumSize(queryPage, () -> sysUserService.list(user)));
    }


    /**
     * 根据ID查询用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "查询详细用户信息", notes = "id存在且大于0")
    @ApiImplicitParam(name = "id", value = "用户编号", required = true, dataType = "Long")
    public Result<SysUser> findById(@PathVariable Long id) {
        if (id == null || id == 0) {
            return new Result<>();
        } else {
            return new Result<>(sysUserService.selectByKey(id));
        }
    }

    /**
     * 添加用户信息
     *
     * @param user
     * @return
     */
    @PostMapping
    @ApiOperation(value = "添加用户")
    @ApiImplicitParam(name = "user", value = "用户实体信息", required = true, dataType = "SysUser", paramType = "body")
    public Result add(@RequestBody SysUser user) {
        sysUserService.create(user);
        return new Result();
    }

    /**
     * 删除用户信息
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户")
    @ApiImplicitParam(name = "id", value = "用户编号", required = true, dataType = "Long")
    public Result delete(@PathVariable Long id) {
        sysUserService.delete(id);
        return new Result();
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @PutMapping("/edit")
    @ApiOperation(value = "更新用户")
    @ApiImplicitParam(name = "user", value = "用户实体信息", required = true, dataType = "SysUser", paramType = "body")
    public Result edit(@RequestBody SysUser user) {
        sysUserService.update(user);
        return new Result();
    }

    /**
     * 修改密码
     *
     * @param user
     * @return
     */
    @PostMapping("/changePass")
    @ApiOperation(value = "修改密码")
    @ApiImplicitParam(name = "user", value = "用户实体信息", required = true, dataType = "SysUser", paramType = "body")
    public Result changePass(@RequestBody SysUser user) {
        sysUserService.changePass(user);
        return new Result();
    }

}
