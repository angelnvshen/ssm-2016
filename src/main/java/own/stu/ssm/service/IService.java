package own.stu.ssm.service;

import java.util.List;

/**
 * Created by dell on 2016/9/27.
 */
public interface IService<T> {
    T selectByKey(Object key);

    int save(T entity);

    int delete(Object key);

    int updateAll(T entity);

    int updateNotNull(T entity);

    List<T> selectByExample(Object example);
}
