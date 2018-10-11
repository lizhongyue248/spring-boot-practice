package cn.echocow.web.service.impl;

import cn.echocow.web.entity.Student;
import cn.echocow.web.repository.StudentRepository;
import cn.echocow.web.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * StudentServiceImpl
 *
 * @author echo
 * @version 1.0
 * @date 18-10-9 下午11:42
 */
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    @Cacheable("students")
    public Page<Student> findAllByPage(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }


    @Override
    @CacheEvict(cacheNames = "students", allEntries = true)
    public void deleteBatch(Long[] ids) {
        studentRepository.deleteBatch(ids);
    }

    @Override
    @CacheEvict(cacheNames = "students", allEntries = true)
    public void saveOrUpdate(Student student) {
        studentRepository.save(student);
    }


}
