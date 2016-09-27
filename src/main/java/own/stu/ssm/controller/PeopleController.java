package own.stu.ssm.controller;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import own.stu.ssm.model.People;
import own.stu.ssm.model.User;
import own.stu.ssm.service.IPeopleService;
import own.stu.ssm.service.IUserService;
import own.stu.ssm.util.CrypographyUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/people")
public class PeopleController {
	Logger log = LoggerFactory.getLogger(PeopleController.class);

    @Resource
    private IPeopleService peopleService;

    private String redirect_list = "redirect:showUser";

    private String index = "showUser";
    @RequestMapping("/getOne")
    public String getPeopleById(People people, Model model) {
        People p = peopleService.selectByKey(people);
        model.addAttribute("people", p);
        return index;
    }

    @RequestMapping("/save")
    public String save(Model model){
        People p = new People();
        p.setAge(21);
        p.setPassWord("123456");
        p.setUserName("MENG");
        int result = peopleService.save(p);

        model.addAttribute("result", result);
        model.addAttribute("people", p);
        return index;
    }

    @RequestMapping("delete")
    public String delete(Integer id) {
        peopleService.delete(id);
        return index;
    }
}
