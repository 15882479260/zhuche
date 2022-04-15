package io.renren.modules.generator.controller;

import java.util.Arrays;

import java.util.Map;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.generator.entity.CarEntity;
import io.renren.modules.generator.service.CarService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-03-24 14:28:54
 */
@RestController
@RequestMapping("generator/car")
public class CarController {
    @Autowired
    private CarService carService;


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:car:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page ;
       switch ((String) params.get("group")){
           case "0":page = carService.queryPage(params);
                    break;
           case "1":page = carService.queryInspectionPage(params);
                    break;
           case "2":page = carService.queryRenewInsurancePage(params);
                    break;
           case "3":page = carService.queryMaintainedPage(params);
                    break;
           default:
               return null;

       }

        return R.ok().put("page", page);
    }


    /**
     * 门店车辆分组列表
     */
    @RequestMapping("/grouplist")
    @RequiresPermissions("generator:car:grouplist")
    public R grouplist(@RequestParam Map<String, Object> params){
        PageUtils page = carService.groupVehicleByStore(params);

        return R.ok().put("page", page);
    }


    /**
     * 根据车型全名获取car列表
     */
    @RequestMapping("/groupcarlist")
    @RequiresPermissions("generator:car:grouplist")
    public R carlistByVehiclefullname(@RequestParam Map<String, Object> params){
        PageUtils page = carService.groupCarByVehiclefullname(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:car:info")
    public R info(@PathVariable("id") Integer id){
		CarEntity car = carService.getById(id);

        return R.ok().put("car", car);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:car:save")
    public R save(@RequestBody CarEntity car){

        car.setVehiclefullname();
		carService.save(car);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:car:update")
    public R update(@RequestBody CarEntity car){

        car.setVehiclefullname();
		carService.updateById(car);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:car:delete")
    public R delete(@RequestBody Integer[] ids){
		carService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
