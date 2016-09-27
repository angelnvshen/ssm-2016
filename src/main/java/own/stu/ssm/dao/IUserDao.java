package own.stu.ssm.dao;

import org.springframework.stereotype.Repository;
import own.stu.ssm.model.User;

// @Repository
public interface IUserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}