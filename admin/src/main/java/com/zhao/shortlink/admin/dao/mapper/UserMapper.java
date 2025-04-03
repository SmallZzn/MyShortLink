

package com.zhao.shortlink.admin.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.shortlink.admin.dao.entity.UserDO;
import org.apache.ibatis.annotations.Select;

/**
 * 用户持久层
 * 小赵
 */
public interface UserMapper extends BaseMapper<UserDO> {

    @Select("SELECT COUNT(*) FROM t_user WHERE DATE(create_time) = CURRENT_DATE()")
    Integer selectTodayNewUser();

    @Select("SELECT COUNT(*) FROM t_user WHERE DATE(create_time) = DATE_SUB(CURRENT_DATE(), INTERVAL 1 DAY)")
    Integer selectYesterdayNewUser();
}
