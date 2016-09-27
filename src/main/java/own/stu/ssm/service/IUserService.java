package own.stu.ssm.service;

import own.stu.ssm.model.People;
import own.stu.ssm.model.User;

import java.util.Set;

public interface IUserService  extends IService<User>{

    /**
     * 通过用户名查询用户
     * @param userName
     * @return
     */
    public User getByUserName(String userName);

    /**
     * 通过用户名查询角色信息
     * @param userName
     * @return
     */
    public Set<String> getRoles(String userName);

    /**
     * 通过用户名查询权限信息
     * @param userName
     * @return
     */
    public Set<String> getPermissions(String userName);
}  