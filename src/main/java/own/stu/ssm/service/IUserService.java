package own.stu.ssm.service;

import own.stu.ssm.model.User;

import java.util.Set;

public interface IUserService {
    public User getUserById(int userId);

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