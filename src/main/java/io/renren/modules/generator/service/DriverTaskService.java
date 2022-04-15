package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.DriverTaskEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-04-14 09:49:17
 */
public interface DriverTaskService extends IService<DriverTaskEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

