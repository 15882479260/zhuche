package io.renren.modules.generator.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.generator.entity.OrderEntity;
import io.renren.modules.generator.service.OrderService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;


/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-04-11 11:18:46
 */
@RestController
@RequestMapping("generator/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:order:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{baseorder}")
    @RequiresPermissions("generator:order:info")
    public R info(@PathVariable("baseorder") Integer baseorder) {
        OrderEntity order = orderService.getById(baseorder);

        return R.ok().put("order", order);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:order:save")
    public R save(@RequestBody OrderEntity order) {
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("carId", order.getCardetail().getIntValue("id"));
        query.put("PickupTime", order.getBaseorder().getString("PickupTime"));
        query.put("PickoffTime", order.getBaseorder().getString("PickoffTime"));
        boolean can = orderService.canCreatOrder(query);
        if (can) {
            orderService.save(order);
            return R.ok();
        } else {
            return R.error(1000, "车辆时间冲突");
        }


    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:order:update")
    public R update(@RequestBody OrderEntity order) {
        orderService.updateById(order);
        return R.ok();
    }

    /**
     * 续租
     */
    @RequestMapping("/renew")
    @RequiresPermissions("generator:order:renew")
    public R renew(@RequestBody OrderEntity order) {
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("carId", order.getCardetail().getIntValue("id"));
        query.put("id", order.getId());
        String BookingPickOffTime=order.getBaseorder().getString("BookingPickOffTime");
        String PickoffTime=order.getBaseorder().getString("PickoffTime");
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");//格式化
        String nowTime= LocalDateTime.now().format(sdf);//日期转String

        if(BookingPickOffTime.compareTo(nowTime)<=0||BookingPickOffTime.compareTo(PickoffTime)<=0){

            return R.error(1000, "续租日期不能小于当前日期或预定还车日期");
        };
        query.put("BookingPickupTime", nowTime);
        query.put("BookingPickOffTime", BookingPickOffTime);
        boolean can = orderService.canRenewOrder(query);
        if (can) {
            order.getBaseorder().put("BookingPickupTime",nowTime);
            order.getBaseorder().put("BookingPickOffTime",BookingPickOffTime);
            orderService.updateById(order);
            return R.ok();
        } else {
            return R.error(1000, "车辆时间冲突");
        }

    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:order:delete")
    public R delete(@RequestBody Integer[] baseorders) {
        orderService.removeByIds(Arrays.asList(baseorders));

        return R.ok();
    }

}
