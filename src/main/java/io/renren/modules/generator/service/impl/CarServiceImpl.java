package io.renren.modules.generator.service.impl;


import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

        page.getRecords().forEach((car)->{
            car.setInsuranceday();
        });

        return new PageUtils(page);
    }


    @Override
    public PageUtils queryMaintainedPage(Map<String, Object> params) {
        IPage<CarEntity> page = this.page(
                new Query<CarEntity>().getPage(params),
                new QueryWrapper<CarEntity>().like(params.get("key")!=null, "CarLicenceNum",params.get("key"))
                        .apply("CurrentMileage % 5000 >=4000")
        );

        page.getRecords().forEach((car)->{
            car.setInsuranceday();
        });

        return new PageUtils(page);
    }


    @Override
    public PageUtils queryInspectionPage(Map<String, Object> params) {

        LocalDate date = LocalDate.now();
        date= date.plusDays(30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String queryData=date.format(formatter);

        IPage<CarEntity> page = this.page(
                new Query<CarEntity>().getPage(params),
                new QueryWrapper<CarEntity>().like(params.get("key")!=null, "CarLicenceNum",params.get("key"))
                        .apply("AnnualInspectionCertificate -> '$.dueDate' <'" +queryData+"'")
        );

        page.getRecords().forEach((car)->{
            car.setInsuranceday();
        });

        return new PageUtils(page);
    }



    @Override
    public PageUtils queryRenewInsurancePage(Map<String, Object> params) {

        LocalDate date = LocalDate.now();
        date= date.plusDays(30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String queryData=date.format(formatter);

        IPage<CarEntity> page = this.page(
                new Query<CarEntity>().getPage(params),
                new QueryWrapper<CarEntity>().like(params.get("key")!=null, "CarLicenceNum",params.get("key"))
                        .apply("CommercialInsurancePolicy -> '$.dueDate' <'" +queryData+"'")
                        .or()
                        .apply("CompulsoryInsurancePolicy -> '$.dueDate' <'" +queryData+"'")
        );

        page.getRecords().forEach((car)->{
            car.setInsuranceday();
        });

        return new PageUtils(page);
    }

    public PageUtils groupVehicleByStore(Map<String, Object> params) {

        QueryWrapper<CarEntity> queryWrapper = new QueryWrapper<CarEntity>();

        IPage<CarEntity> page = this.page(
                new Query<CarEntity>().getPage(params),
                queryWrapper
                        .apply("Store -> '$.id' = "+params.get("storeId")+"")
                        .groupBy("vehiclefullname")
        );

        return new PageUtils(page);
    }

    public PageUtils groupCarByVehiclefullname(Map<String, Object> params) {

        QueryWrapper<CarEntity> queryWrapper = new QueryWrapper<CarEntity>();

        IPage<CarEntity> page = this.page(
                new Query<CarEntity>().getPage(params),
                queryWrapper.eq(params.get("vehiclefullname")!=null&&!params.get("vehiclefullname").equals(""),"vehiclefullname",params.get("vehiclefullname"))
        );

        return new PageUtils(page);
    }
}