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

        String BookingPickupTime =order.getBaseorder().getString("BookingPickupTime");
        String BookingPickoffTime =order.getBaseorder().getString("BookingPickoffTime");
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");//格式化
        String nowTime= LocalDateTime.now().format(sdf);//日期转String

        if(BookingPickupTime.compareTo(nowTime)<=0||BookingPickoffTime.compareTo(BookingPickupTime)<=0){
            return R.error(1000, "预定开始时间和结束时间须大于当前时间");
        };

        Map<String,String> result = orderService.canCreatOrder(order);
        if (result.get("isOk").equals("true")) {
            orderService.save(order);
            return R.ok();
        } else {
            return R.error(1000, result.get("msg"));
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

        OrderEntity oldOrder= orderService.getById(order.getId());

        String PickoffTime=order.getBaseorder().getString("PickoffTime");
        String BookingPickoffTime=order.getBaseorder().getString("BookingPickoffTime");
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");//格式化
        String nowTime= LocalDateTime.now().format(sdf);//日期转String

        if(PickoffTime.compareTo(nowTime)<=0||PickoffTime.compareTo(BookingPickoffTime)<=0){
            return R.error(1000, "续租时间须大于当前时间和预定还车时间");
        };

        Map<String,String> result = orderService.canRenewOrder(order,oldOrder);

        if (result.get("isOk").equals("true")) {
            orderService.updateById(order);
            return R.ok();
        } else {
            return R.error(1000, result.get("msg"));
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
