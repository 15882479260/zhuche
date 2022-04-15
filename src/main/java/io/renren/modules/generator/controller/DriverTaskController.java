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

import io.renren.modules.generator.entity.DriverTaskEntity;
import io.renren.modules.generator.service.DriverTaskService;
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
@RequestMapping("generator/drivertask")
public class DriverTaskController {
    @Autowired
    private DriverTaskService driverTaskService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:drivertask:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = driverTaskService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:drivertask:info")
    public R info(@PathVariable("id") Integer id){
		DriverTaskEntity driverTask = driverTaskService.getById(id);

        return R.ok().put("driverTask", driverTask);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:drivertask:save")
    public R save(@RequestBody DriverTaskEntity driverTask){
		driverTaskService.save(driverTask);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:drivertask:update")
    public R update(@RequestBody DriverTaskEntity driverTask){
		driverTaskService.updateById(driverTask);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:drivertask:delete")
    public R delete(@RequestBody Integer[] ids){
		driverTaskService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
