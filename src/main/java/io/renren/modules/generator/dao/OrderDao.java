package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-04-11 11:18:46
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
