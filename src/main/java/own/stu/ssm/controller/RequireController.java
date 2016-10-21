package own.stu.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dell on 2016/10/21.
 */
@Controller
@RequestMapping("/require")
public class RequireController {

    @RequestMapping("hw")
    public String testHelloWorld(){
        return "require/helloWorld";
    }

    @RequestMapping("app")
    public String testApp(){
        return "app";
    }
}
