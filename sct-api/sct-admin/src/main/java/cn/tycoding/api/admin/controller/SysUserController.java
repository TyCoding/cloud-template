package cn.tycoding.api.admin.controller;

import cn.tycoding.api.admin.entity.SysUser;
import cn.tycoding.api.admin.service.SysUserService;
import cn.tycoding.api.common.controller.BaseController;
import cn.tycoding.api.common.utils.QueryPage;
import cn.tycoding.api.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.Map;

/**
 * @author tycoding
 * @date 2019-05-22
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取当前用户信息
     *
     * @return
     */
    @GetMapping("/info")
    public Result<SysUser> info() {
        return new Result<>();
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    @GetMapping("/info/{username}")
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
    public Result<Map> list(SysUser user, QueryPage queryPage) {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            criteria.andCondition("username=", user.getUsername());
        }
        if (user.getPhone() != null && !user.getPhone().isEmpty()) {
            criteria.andCondition("phone", user.getPhone());
        }
        return new Result<Map>(this.selectByPageNumSize(queryPage, () -> sysUserService.selectByExample(example)));
    }


    /**
     * 根据ID查询用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
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
    public Result add(@RequestBody SysUser user) {
        sysUserService.save(user);
        return new Result();
    }

    /**
     * 删除用户信息
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
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
    public Result edit(@RequestBody SysUser user) {
        sysUserService.updateNotNull(user);
        return new Result();
    }

}
