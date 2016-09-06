package own.stu.ssm.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by dell on 2016/9/2.
 */
public class HelloWorld {

    @Test
    public void testIni(){
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");

        SecurityManager manager = factory.getInstance();

        SecurityUtils.setSecurityManager(manager);

        //当前登录用户
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("admin","123456");

        subject.login(token);

        subject.logout();
    }

    @Test
    public void testJdbc(){
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:jdbc_realm.ini");
        SecurityManager manager = factory.getInstance();

        SecurityUtils.setSecurityManager(manager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("admin","123456");

        subject.login(token);

        subject.logout();

    }

    @Test
    public void testHasRole(){
//        Subject subject = getSubject("shiro_role.ini","admin","123456");
        Subject subject = getSubject("shiro_role.ini","qin","123456");
        System.out.println(subject.hasRole("admin")?"has Admin role":"has not Admin role");
        boolean [] hasRoles = subject.hasRoles(Arrays.asList("admin","guest"));
        System.out.println(hasRoles[0]?"has Admin role":"has not Admin role");
        System.out.println(hasRoles[1]?"has Guest role":"has not Guest role");

        System.out.println(subject.hasAllRoles(Arrays.asList("admin", "guest"))?"has all roles ":"has not all roles");
    }

    @Test
    public void tesCheckRole(){
        Subject subject = getSubject("shiro_role.ini","admin","123456");
//        Subject subject = getSubject("shiro_role.ini","qin","123456");
        subject.checkRole("admin");
        subject.checkRoles(Arrays.asList("admin","guest"));
    }

    public Subject getSubject(String configFile, String name, String password){
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:"+configFile);
        SecurityManager manager = factory.getInstance();

        SecurityUtils.setSecurityManager(manager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(name,password);

        subject.login(token);

        return subject;
    }
}
