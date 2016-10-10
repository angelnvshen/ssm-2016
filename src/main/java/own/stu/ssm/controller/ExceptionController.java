package own.stu.ssm.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by CHANEL on 2016/10/6.
 */
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler
    public ModelAndView exceptionHandler(Exception ex){
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("exMsg", ex);
        System.out.println("exception handler");
        System.out.println("Global exception handler");
        return mv;
    }

}
