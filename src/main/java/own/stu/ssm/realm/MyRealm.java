package own.stu.ssm.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import own.stu.ssm.dao.UserDao;
import own.stu.ssm.model.User;
import own.stu.ssm.service.IUserService;

import javax.annotation.Resource;
import java.sql.Connection;

/**
 * Created by dell on 2016/9/20.
 */
public class MyRealm extends AuthorizingRealm {

    @Resource
    private IUserService userService;
    /**
     * 为当前登录的用户授予角色和权限
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String userName = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        info.setRoles(userService.getRoles( userName));
        info.setStringPermissions(userService.getPermissions( userName));

        return info;
    }

    /**
     * 验证当前登录的用户
     * @param authenticationToken
     * @return
     * @throws org.apache.shiro.authc.AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();

        User user = userService.getByUserName( userName);
        if(user != null){
            AuthenticationInfo info = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), "xxx");
            return info;
        }

        return null;
    }
}
