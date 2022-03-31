package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.CarEntity;

import java.text.ParseException;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-03-24 14:28:54
 */
public interface CarService extends IService<CarEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryInspectionPage(Map<String, Object> params);

    PageUtils queryRenewInsurancePage(Map<String, Object> params);

    PageUtils  queryMaintainedPage(Map<String, Object> params);
}

