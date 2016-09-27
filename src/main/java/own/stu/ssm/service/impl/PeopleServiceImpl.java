package own.stu.ssm.service.impl;


import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import own.stu.ssm.model.People;
import own.stu.ssm.service.IPeopleService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service("peopleService")
public class PeopleServiceImpl extends BaseService<People> implements IPeopleService {

    @Override
    public List<People> selectByCountry(People people, int page, int rows) {
        Example example = new Example(People.class);
        Example.Criteria criteria = example.createCriteria();

        if(StringUtil.isNotEmpty(people.getUserName())){
            criteria.andLike("user_name", "%" + people.getUserName() + "%");
        }
        if (people.getPassWord() != null) {
            criteria.andEqualTo("pass_word", people.getPassWord());
        }
        if (people.getId() != null) {
            criteria.andEqualTo("id", people.getId());
        }
        //分页查询
        PageHelper.startPage(page, rows);
        return selectByExample(example);
    }
}
