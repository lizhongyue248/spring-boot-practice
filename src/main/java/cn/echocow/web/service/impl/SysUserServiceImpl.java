package cn.echocow.web.service.impl;

import cn.echocow.web.entity.SysUser;
import cn.echocow.web.repository.SysUserRepository;
import cn.echocow.web.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * SysUserServiceImpl
 *
 * @author echo
 * @version 1.0
 * @date 18-10-9 下午11:43
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    private final SysUserRepository sysUserRepository;

    @Autowired
    public SysUserServiceImpl(SysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    @Override
    @Cacheable(cacheNames = "login", key = "#user")
    public SysUser findByUser(SysUser user) {
        return sysUserRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }


    @Override
    @CachePut(cacheNames = "login", key = "#user")
    public SysUser save(SysUser user) {
        if (sysUserRepository.findByUsername(user.getUsername()) != null) {
            return null;
        } else {
            return sysUserRepository.save(user);
        }
    }
}
