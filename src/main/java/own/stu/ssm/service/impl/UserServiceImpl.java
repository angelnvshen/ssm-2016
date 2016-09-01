package own.stu.ssm.service.impl;
import javax.annotation.Resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import own.stu.ssm.dao.IUserDao;
import own.stu.ssm.model.User;
import own.stu.ssm.service.IUserService;


@Service("userService")  
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;
    
    public User getUserById(int userId) {
        // TODO Auto-generated method stub  
        return this.userDao.selectByPrimaryKey(userId);  
    }  
  
}  
