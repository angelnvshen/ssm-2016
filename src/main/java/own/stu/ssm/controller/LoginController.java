package own.stu.ssm.controller;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import own.stu.ssm.model.User;
import own.stu.ssm.service.IUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Controller
public class LoginController {
	Logger log = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private IUserService userService;

    /**
     * 登录页面
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return  "login";
    }

    /**
     * 登录页面
     * @return
     */
    @RequestMapping("/success")
    public String success(){
        return  "success";
    }

    
}
