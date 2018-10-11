package cn.echocow.web.controller.api;

import cn.echocow.web.entity.Response;
import cn.echocow.web.entity.Student;
import cn.echocow.web.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * StudentController
 *
 * @author echo
 * @version 1.0
 * @date 18-10-10 下午1:00
 */
@RestController
@RequestMapping("/api")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @DeleteMapping("/student/{id}")
    public Response delete(@PathVariable("id") Long[] ids) {
        studentService.deleteBatch(ids);
        return new Response(HttpStatus.OK);
    }

    @PutMapping("/student")
    public Response saveOrUpdate(Student student) {
        studentService.saveOrUpdate(student);
        return new Response(HttpStatus.OK);
    }

}
