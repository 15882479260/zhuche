package io.renren.modules.generator.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.CarDao;
import io.renren.modules.generator.entity.CarEntity;
import io.renren.modules.generator.service.CarService;


@Service("carService")
public class CarServiceImpl extends ServiceImpl<CarDao, CarEntity> implements CarService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CarEntity> page = this.page(
                new Query<CarEntity>().getPage(params),
                new QueryWrapper<CarEntity>().like(params.get("key")!=null, "CarLicenceNum",params.get("key"))
        );

        return new PageUtils(page);
    }


    @Override
    public PageUtils queryInsuranceDaysCars(Map<String, Object> params) {
        IPage<CarEntity> page = this.page(
                new Query<CarEntity>().getPage(params),
                new QueryWrapper<CarEntity>().le( "InsuranceDays",30)
        );

        return new PageUtils(page);
    }

}