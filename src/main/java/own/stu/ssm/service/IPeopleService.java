package own.stu.ssm.service;

import own.stu.ssm.model.People;
import own.stu.ssm.model.User;

import java.util.List;
import java.util.Set;

public interface IPeopleService extends IService<People>{
    /**
     * 根据条件分页查询
     *
     * @param people
     * @param page
     * @param rows
     * @return
     */
    List<People> selectByCountry(People people, int page, int rows);
}  