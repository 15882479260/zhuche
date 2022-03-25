package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.CarEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-03-24 14:28:54
 */
@Mapper
public interface CarDao extends BaseMapper<CarEntity> {
	
}
