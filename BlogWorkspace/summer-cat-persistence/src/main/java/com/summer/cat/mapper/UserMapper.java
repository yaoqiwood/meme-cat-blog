package com.summer.cat.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.summer.cat.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author summer
 * @since 2018-05-03
 */
public interface UserMapper extends BaseMapper<User> {


    //等同于编写一个普通 list 查询，mybatis-plus 自动替你分页
    List<User> selectPageByConditionUser(Page<User> page, @Param("info") String info,
                                         @Param("status") Integer [] status, @Param("startTime")String startTime, @Param("endTime")String endTime);


}
