package cn.echocow.web.repository;

import cn.echocow.web.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * StudentRepository
 *
 * @author echo
 * @version 1.0
 * @date 18-10-9 下午3:25
 */
public interface StudentRepository extends JpaRepository<Student,Long> {

    /**
     * 批量删除
     *
     * @param ids 删除的id
     */
    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("delete from Student s where s.id in (?1)")
    void deleteBatch(Long[] ids);
}
