package own.stu.ssm.dao;

import org.springframework.stereotype.Repository;
import own.stu.ssm.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dell on 2016/9/20.
 */
@Repository
public interface UserDao {

    public User getByUserName( String userName) ;

    public Set<String> getRoles( String userName) ;

    public Set<String> getPermissions( String userName);
}
