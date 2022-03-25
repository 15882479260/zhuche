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
                new QueryWrapper<CarEntity>()
        );

        return new PageUtils(page);
    }

}