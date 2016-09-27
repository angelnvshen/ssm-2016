package own.stu.ssm.service.impl;
import javax.annotation.Resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import own.stu.ssm.dao.IUserDao;
import own.stu.ssm.dao.UserDao;
import own.stu.ssm.model.User;
import own.stu.ssm.service.IUserService;

import java.util.Set;


@Service("userService")  
public class UserServiceImpl  extends BaseService<User> implements  IUserService {

   @Resource
   private UserDao userdao;

   public User getByUserName(String userName) {
        return userdao.getByUserName(userName);
    }

    public Set<String> getRoles(String userName) {
        return userdao.getRoles(userName);
    }

    public Set<String> getPermissions(String userName) {
        return userdao.getPermissions(userName);
    }
}  
