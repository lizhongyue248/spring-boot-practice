package cn.echocow.web.controller;

import cn.echocow.web.entity.Student;
import cn.echocow.web.entity.SysUser;
import cn.echocow.web.service.StudentService;
import cn.echocow.web.service.SysUserService;
import com.google.code.kaptcha.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * MainController
 *
 * @author echo
 * @version 1.0
 * @date 18-10-9 下午3:26
 */
@Controller
public class MainController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final SysUserService sysUserService;

    private final StudentService studentService;

    @Autowired
    public MainController(SysUserService sysUserService, StudentService studentService) {
        this.sysUserService = sysUserService;
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String index(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable, HttpSession session, Model model) {
        Object user = session.getAttribute("user");
        Page<Student> page = null;
        if (user != null) {
            page = studentService.findAllByPage(pageable);
        }
        model.addAttribute("user", user)
                .addAttribute("page", page);
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new SysUser())
                .addAttribute("msg", null);
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute SysUser user, String captcha, HttpSession session, Model model) {
        if (captcha == null || !captcha.equals(session.getAttribute(Constants.KAPTCHA_SESSION_KEY))) {
            model.addAttribute("msg", "Error:Captcha is error!")
                    .addAttribute("user", user);
            return "login";
        }
        SysUser login = sysUserService.findByUser(user);
        if (login == null) {
            model.addAttribute("msg", "Error:User not exist or password is error!")
                    .addAttribute("user", user);
            return "login";
        } else {
            logger.info("User:" + login.getUsername() + " login success!");
            session.setAttribute("user", login);
            return "redirect:/";
        }
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new SysUser())
                .addAttribute("msg", null);
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute SysUser user, String captcha, HttpSession session, Model model) {
        if (captcha == null || !captcha.equals(session.getAttribute(Constants.KAPTCHA_SESSION_KEY))) {
            model.addAttribute("msg", "Error:Captcha is error!")
                    .addAttribute("user", user);
            return "register";
        }
        SysUser login = sysUserService.save(user);
        if (login == null) {
            model.addAttribute("msg", "Error:The Account is exist.")
                    .addAttribute("user", user);
            return "register";
        } else {
            logger.info("User:" + login.getUsername() + " register and login success!");
            session.setAttribute("user", login);
            return "redirect:/";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }
}
