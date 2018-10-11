package cn.echocow.web.service;

import cn.echocow.web.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * StudentService
 *
 * @author echo
 * @version 1.0
 * @date 18-10-9 下午3:39
 */
public interface StudentService {
    /**
     * 分页查询
     *
     * @param pageable 分页
     * @return 结果
     */
    Page<Student> findAllByPage(Pageable pageable);

    /**
     * 批量删除
     *
     * @param ids 删除的id
     */
    void deleteBatch(Long[] ids);

    /**
     * 保存或更新
     *
     * @param student 学生
     */
    void saveOrUpdate(Student student);
}
