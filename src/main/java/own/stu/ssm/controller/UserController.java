package own.stu.ssm.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import own.stu.ssm.util.CrypographyUtil;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
	Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource
    private IUserService userService;

    /**
     * 用户登录
     * @param user
     * @param request
     * @return
     */
    @RequestMapping("/login")
    public String login(User user, HttpServletRequest request){
        Subject subject= SecurityUtils.getSubject();
        user.setPassword(CrypographyUtil.encMd5(user.getPassword()));
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUserName(), user.getPassword());
        try {
            subject.login(token);
            return "redirect:/success"; //登录成功 跳转
        }catch (Exception e){
            e.printStackTrace();
            request.setAttribute("errorInfo", "用户名或密码错误");
            return  "login";
        }
    }

    @RequestMapping(value="/test",method=RequestMethod.GET)
    public String test(HttpServletRequest request,Model model){  
        int userId = Integer.parseInt(request.getParameter("id"));  
        System.out.println("userId:"+userId);
        User user=null;
        if (userId==1) {
             user = new User();  
             user.setAge(11);
             user.setId(1);
             user.setPassword("123");
             user.setUserName("javen");
            log.debug(user.toString());
            model.addAttribute("user", user);
        }
        return "index";
    }

    // /user/showUser?id=1
    @RequestMapping(value="/showUser",method=RequestMethod.GET)
    public String toIndex(HttpServletRequest request,Model model){
        int userId = Integer.parseInt(request.getParameter("id"));
        System.out.println("userId:"+userId);
        User user = this.userService.getUserById(userId);
        log.debug(user.toString());
        model.addAttribute("user", user);
        return "showUser";
    }

    // /user/showUser2?id=1
    @RequestMapping(value="/showUser2",method=RequestMethod.GET)
    public String toIndex2(@RequestParam("id") String id,Model model){
        int userId = Integer.parseInt(id);
        System.out.println("userId:"+userId);
        User user = this.userService.getUserById(userId);
        log.debug(user.toString());
        model.addAttribute("user", user);
        return "showUser";
    }

    // /user/showUser3/{id}
    @RequestMapping(value="/showUser3/{id}",method=RequestMethod.GET)
    public String toIndex3(@PathVariable("id")String id,Map<String, Object> model){
        int userId = Integer.parseInt(id);
        System.out.println("userId:"+userId);
        User user = this.userService.getUserById(userId);
        log.debug(user.toString());
        model.put("user", user);
        return "showUser";
    }

    // /user/{id}
    @ResponseBody
    @RequestMapping(value="/jsontype/{id}",method=RequestMethod.GET)
    public User getUserInJson(@PathVariable String id,Map<String, Object> model){
        int userId = Integer.parseInt(id);
        System.out.println("userId:"+userId);
        User user = this.userService.getUserById(userId);
        log.info(user.toString());
        return user;
    }

    // /user/{id}
    @RequestMapping(value="/jsontype2/{id}",method=RequestMethod.GET)
    public ResponseEntity<User>  getUserInJson2(@PathVariable String id,Map<String, Object> model){
        int userId = Integer.parseInt(id);
        System.out.println("userId:"+userId);
        User user = this.userService.getUserById(userId);
        log.info(user.toString());
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    //文件上传、
    @RequestMapping(value="/upload")
    public String showUploadPage(){
        return "user_admin/file";
    }

    @RequestMapping(value="/doUpload",method=RequestMethod.POST)
    public String doUploadFile(@RequestParam("file")MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            log.info("Process file:{}",file.getOriginalFilename());
        }
        FileUtils.copyInputStreamToFile(file.getInputStream(), new File("E:\\", System.currentTimeMillis() + file.getOriginalFilename()));
        return "success";
    }
    
}
