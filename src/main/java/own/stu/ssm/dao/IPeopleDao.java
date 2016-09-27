package own.stu.ssm.dao;

import org.springframework.stereotype.Repository;
import own.stu.ssm.model.People;
import own.stu.ssm.util.CommonMapper;

/**
 * Created by dell on 2016/9/27.
 */
@Repository
public interface IPeopleDao extends CommonMapper<People> {
}
