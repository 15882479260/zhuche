package io.renren.modules.generator.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.DriverDao;
import io.renren.modules.generator.entity.DriverEntity;
import io.renren.modules.generator.service.DriverService;


@Service("driverService")
public class DriverServiceImpl extends ServiceImpl<DriverDao, DriverEntity> implements DriverService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DriverEntity> page = this.page(
                new Query<DriverEntity>().getPage(params),
                new QueryWrapper<DriverEntity>()
        );

        return new PageUtils(page);
    }

}