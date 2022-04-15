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

import io.renren.modules.generator.entity.DriverEntity;
import io.renren.modules.generator.service.DriverService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-04-14 09:49:17
 */
@RestController
@RequestMapping("generator/driver")
public class DriverController {
    @Autowired
    private DriverService driverService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:driver:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = driverService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:driver:info")
    public R info(@PathVariable("id") Integer id){
		DriverEntity driver = driverService.getById(id);

        return R.ok().put("driver", driver);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:driver:save")
    public R save(@RequestBody DriverEntity driver){
		driverService.save(driver);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:driver:update")
    public R update(@RequestBody DriverEntity driver){
		driverService.updateById(driver);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:driver:delete")
    public R delete(@RequestBody Integer[] ids){
		driverService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
