package own.stu.ssm.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by dell on 2016/9/27.
 */
public interface CommonMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
