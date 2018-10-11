package cn.echocow.web.service;

import cn.echocow.web.entity.SysUser;

/**
 * SysUserService
 *
 * @author echo
 * @version 1.0
 * @date 18-10-9 下午3:38
 */
public interface SysUserService {
    /**
     * 用户
     *
     * @param user 用户
     * @return 用户
     */
    SysUser findByUser(SysUser user);



    /**
     * 注册之保存用户
     *
     * @param user 用户
     * @return 注册成功的用户
     */
    SysUser save(SysUser user);
}
