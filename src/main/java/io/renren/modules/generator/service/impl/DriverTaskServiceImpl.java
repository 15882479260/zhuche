package io.renren.modules.generator.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.DriverTaskDao;
import io.renren.modules.generator.entity.DriverTaskEntity;
import io.renren.modules.generator.service.DriverTaskService;


@Service("driverTaskService")
public class DriverTaskServiceImpl extends ServiceImpl<DriverTaskDao, DriverTaskEntity> implements DriverTaskService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DriverTaskEntity> page = this.page(
                new Query<DriverTaskEntity>().getPage(params),
                new QueryWrapper<DriverTaskEntity>().eq(params.get("driverId")!=null,"driverId",params.get("driverId"))
        );

        return new PageUtils(page);
    }

}