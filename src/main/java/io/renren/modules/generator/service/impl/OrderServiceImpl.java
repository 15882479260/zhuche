package io.renren.modules.generator.service.impl;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.OrderDao;
import io.renren.modules.generator.entity.OrderEntity;
import io.renren.modules.generator.service.OrderService;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    public Map canCreatOrder(OrderEntity order) {
        Map<String,Object> params=new HashMap<>();
        Map<String,String> result=new HashMap<>();
        int carId=order.getCardetail().getIntValue("id");
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>().apply("cardetail -> '$.id' =" + carId + " AND baseorder -> '$.status' != '已还车' AND baseorder -> '$.status' != '已取消'")
        );
        String BookingPickupTime1 = order.getBaseorder().getString("BookingPickupTime");
        String BookingPickoffTime1 = order.getBaseorder().getString("BookingPickoffTime");

        List<OrderEntity> orderList = page.getRecords();
        for (int i = 0; i < orderList.size(); i++) {
            OrderEntity otherOrder = orderList.get(i);
            String BookingPickupTime = otherOrder.getBaseorder().getString("BookingPickupTime");
            String BookingPickoffTime = otherOrder.getBaseorder().getString("BookingPickoffTime");
            String PickoffTime = otherOrder.getBaseorder().getString("PickoffTime"); // 续期结束时间
            if (PickoffTime != null) {
                if (BookingPickupTime1.compareTo(BookingPickupTime) >= 0 && BookingPickupTime1.compareTo(PickoffTime) <= 0
                        || BookingPickoffTime1.compareTo(BookingPickupTime) >= 0 && BookingPickoffTime1.compareTo(PickoffTime) <= 0
                        || BookingPickupTime1.compareTo(BookingPickupTime) < 0 && BookingPickoffTime1.compareTo(PickoffTime) > 0) {
                    result.put("isOk","false");
                    result.put("msg","续租时间和其它车辆续租时间冲突");
                    return result;
                }
            } else {
                if (BookingPickupTime1.compareTo(BookingPickupTime) >= 0 && BookingPickupTime1.compareTo(BookingPickoffTime) <= 0
                        || BookingPickoffTime1.compareTo(BookingPickupTime) >= 0 && BookingPickoffTime1.compareTo(BookingPickoffTime) <= 0
                        || BookingPickupTime1.compareTo(BookingPickupTime) < 0 && BookingPickoffTime1.compareTo(BookingPickoffTime) > 0) {
                    result.put("isOk","false");
                    result.put("msg","续租时间和其它车辆预定时间冲突");
                    return result;
                }
            }

        }
        result.put("isOk","true");
        return result;
    }

    public Map canRenewOrder(OrderEntity order, OrderEntity oldOrder) {
        Map<String,Object> params=new HashMap<>();
        Map<String,String> result=new HashMap<>();
        int carId=order.getCardetail().getIntValue("id");
        int orderId=order.getId().intValue();
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>().apply("cardetail -> '$.id' =" + carId + " And ID != "+orderId+" AND baseorder -> '$.status' != '已还车' AND baseorder -> '$.status' != '已取消'")
        );

        String PickoffTime = order.getBaseorder().getString("PickoffTime");
        String PickupTime = "";
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");//格式化
        String nowTime = LocalDateTime.now().format(sdf);

        switch (oldOrder.getBaseorder().getString("status")) {
            case "已还车":
                PickupTime =nowTime;
                break;
            case "已续租":
                String oldPickoffTime = oldOrder.getBaseorder().getString("PickoffTime");//旧的续租还车时间

                if (PickoffTime.compareTo(oldPickoffTime) <= 0) {
                    result.put("isOk","false");
                    result.put("msg","新的续租时间须大于上次续租时间");
                    return result;
                }
                PickupTime = oldPickoffTime;//旧的续租还车时间
                break;
            default:
                PickupTime = oldOrder.getBaseorder().getString("BookingPickoffTime");
                break;
        }

        List<OrderEntity> otherOrderList = page.getRecords();
        for (int i = 0; i < otherOrderList.size(); i++) {
            OrderEntity otherOrder = otherOrderList.get(i);

            String BookingPickupTime = otherOrder.getBaseorder().getString("BookingPickupTime");
            String BookingPickoffTime = otherOrder.getBaseorder().getString("BookingPickoffTime");
            String PickoffTime2 = otherOrder.getBaseorder().getString("PickoffTime");
            if (PickoffTime2 != null) {
                if (PickupTime.compareTo(BookingPickupTime) >= 0 && PickupTime.compareTo(PickoffTime2) <= 0
                        || PickoffTime.compareTo(BookingPickupTime) >= 0 && PickoffTime.compareTo(PickoffTime2) <= 0
                        || PickupTime.compareTo(BookingPickupTime) < 0 && PickoffTime.compareTo(PickoffTime2) > 0) {
                    result.put("isOk","false");
                    result.put("msg","续租时间和其它车辆续租时间冲突");
                    return result;
                }
            } else {
                if (PickupTime.compareTo(BookingPickupTime) >= 0 && PickupTime.compareTo(BookingPickoffTime) <= 0
                        || PickoffTime.compareTo(BookingPickupTime) >= 0 && PickoffTime.compareTo(BookingPickoffTime) <= 0
                        || PickupTime.compareTo(BookingPickupTime) < 0 && PickoffTime.compareTo(BookingPickoffTime) > 0) {
                    result.put("isOk","false");
                    result.put("msg","续租时间和其它车辆预定时间冲突");
                    return result;
                }
            }

        }
        order.getBaseorder().put("PickupTime",PickupTime);//添加预定起始时间
        result.put("isOk","true");
        return result;
    }
}