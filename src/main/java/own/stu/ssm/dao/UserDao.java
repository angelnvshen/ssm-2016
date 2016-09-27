package own.stu.ssm.dao;

import org.springframework.stereotype.Repository;
import own.stu.ssm.model.User;
import own.stu.ssm.util.CommonMapper;

import java.util.Set;

/**
 * Created by dell on 2016/9/20.
 */
@Repository
public interface UserDao  extends CommonMapper<User> {

    public User getByUserName( String userName) ;

    public Set<String> getRoles( String userName) ;

    public Set<String> getPermissions( String userName);
}
