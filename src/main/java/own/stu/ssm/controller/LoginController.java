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
import own.stu.ssm.model.User;
import own.stu.ssm.service.IUserService;
import own.stu.ssm.util.CrypographyUtil;

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

    /**
     * 用户登录
     * @param user
     * @param request
     * @return
     */
    @RequestMapping("/loginOn")
    public String login(User user, HttpServletRequest request){
        Subject subject= SecurityUtils.getSubject();
        user.setPassword(CrypographyUtil.encMd5(user.getPassword()));
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUserName(), user.getPassword());
        try {
            subject.login(token);
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            System.out.println(savedRequest.getQueryString());
            System.out.println(savedRequest.getRequestURI());
            System.out.println(savedRequest.getRequestUrl());
            return "redirect:/success"; //登录成功 跳转
        }catch (Exception e){
            e.printStackTrace();
            request.setAttribute("errorInfo", "用户名或密码错误");
            return  "login";
        }
    }
}
