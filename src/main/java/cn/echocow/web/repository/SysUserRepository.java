package cn.echocow.web.repository;


import cn.echocow.web.entity.SysUser;
import org.springframework.data.repository.CrudRepository;

/**
 * SysUserRepository
 *
 * @author echo
 * @version 1.0
 * @date 18-10-9 下午3:03
 */
public interface SysUserRepository extends CrudRepository<SysUser,Long> {
    /**
     *通过用户名和密码查找用户
     *
     * @param name  用户名
     * @param password 密码
     * @return 查找到的用户
     */
    SysUser findByUsernameAndPassword(String name,String password);

    /**
     * 通过用户名查找用户
     *
     * @param name 用户名
     * @return 用户
     */
    SysUser findByUsername(String name);
}
