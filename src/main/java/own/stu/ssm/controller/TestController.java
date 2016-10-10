package own.stu.ssm.controller;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import own.stu.ssm.model.Country;
import own.stu.ssm.model.Country_entity;
import own.stu.ssm.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CHANEL on 2016/10/6.
 */
@Controller
@RequestMapping("/test")
public class TestController {
    
    @RequestMapping("/date")
    public String testInitBinder(Date date){
        System.out.println(date);
        return "index";
    }

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder){
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler(Exception ex){
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("exMsg", ex);
        System.out.println("exception handler");
        return mv;
    }

    @RequestMapping("/error")
    public String testError(){
        int i = 5/0;
        return "index";
    }

    @ResponseBody
    @RequestMapping(value="/jsontype/{id}")
    public User getUser(@PathVariable String id){
        int userId = Integer.parseInt(id);
        User user = new User();
        user.setAge(22);
        user.setId(userId);
        user.setUserName("Meng");
        return user;
    }

    @RequestMapping("/getData")
    public String getData(){


        return "index";
    }
    @RequestMapping("/getResponseEntity")
    public String getData(Model model){
        final String uri = "http://localhost:8181/ssm_sim/test_RESTful";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Country_entity> date = restTemplate.postForEntity(uri,null, Country_entity.class);
        System.out.println(date.getStatusCode());
        Country_entity result = date.getBody();
        System.out.println(result);
        model.addAttribute("entity", result);
        return "index";
    }

}
