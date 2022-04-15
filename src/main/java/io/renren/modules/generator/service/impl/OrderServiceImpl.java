package io.renren.modules.generator.service.impl;

import org.springframework.stereotype.Service;

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

     public boolean canCreatOrder(Map<String, Object> params){
         IPage<OrderEntity> page = this.page(
                 new Query<OrderEntity>().getPage(params),
                 new QueryWrapper<OrderEntity>().apply("cardetail -> '$.id' =" +params.get("carId")+" AND baseorder -> '$.status' != '已还车' AND baseorder -> '$.status' != '已取消'")
         );
         String PickupTime1=String.valueOf(params.get("PickupTime"));
         String PickoffTime1=String.valueOf(params.get("PickoffTime"));

         List<OrderEntity> orderList= page.getRecords();
         for (int i = 0; i < orderList.size(); i++) {
             OrderEntity order =orderList.get(i);
             String PickupTime= order.getBaseorder().getString("PickupTime");
             String PickoffTime= order.getBaseorder().getString("PickoffTime");
             String BookingPickOffTime= order.getBaseorder().getString("BookingPickOffTime");
             if(BookingPickOffTime!=null){
                 if(PickupTime1.compareTo(PickupTime)>=0&&PickupTime1.compareTo(BookingPickOffTime)<=0
                         ||PickoffTime1.compareTo(PickupTime)>=0&&PickoffTime1.compareTo(BookingPickOffTime)<=0
                         ||PickupTime1.compareTo(PickupTime)<0&&PickoffTime1.compareTo(BookingPickOffTime)>0){
                     return false;
                 }
             }else{
                 if(PickupTime1.compareTo(PickupTime)>=0&&PickupTime1.compareTo(PickoffTime)<=0
                         ||PickoffTime1.compareTo(PickupTime)>=0&&PickoffTime1.compareTo(PickoffTime)<=0
                         ||PickupTime1.compareTo(PickupTime)<0&&PickoffTime1.compareTo(PickoffTime)>0){
                     return false;
                 }
             }

         }

         return true;
     }

    public boolean canRenewOrder(Map<String, Object> params){
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>().apply("cardetail -> '$.id' =" +params.get("carId")+" AND baseorder -> '$.status' != '已还车' AND baseorder -> '$.status' != '已取消'")
        );
        String BookingPickupTime=String.valueOf(params.get("BookingPickupTime"));
        String BookingPickOffTime=String.valueOf(params.get("BookingPickOffTime"));

        List<OrderEntity> orderList= page.getRecords();
        for (int i = 0; i < orderList.size(); i++) {
            OrderEntity order =orderList.get(i);
            if(order.getId().intValue()==Integer.parseInt(String.valueOf(params.get("id")))){
                continue;
            }
            String PickupTime= order.getBaseorder().getString("PickupTime");
            String PickoffTime= order.getBaseorder().getString("PickoffTime");
            String BookingPickOffTime2= order.getBaseorder().getString("BookingPickOffTime");
            if(BookingPickOffTime2!=null){
                if(BookingPickupTime.compareTo(PickupTime)>=0&&BookingPickupTime.compareTo(BookingPickOffTime2)<=0
                        ||BookingPickOffTime.compareTo(PickupTime)>=0&&BookingPickOffTime.compareTo(BookingPickOffTime2)<=0
                        ||BookingPickupTime.compareTo(PickupTime)<0&&BookingPickOffTime.compareTo(BookingPickOffTime2)>0){
                    return false;
                }
            }else{
                if(BookingPickupTime.compareTo(PickupTime)>=0&&BookingPickupTime.compareTo(PickoffTime)<=0
                        ||BookingPickOffTime.compareTo(PickupTime)>=0&&BookingPickOffTime.compareTo(PickoffTime)<=0
                        ||BookingPickupTime.compareTo(PickupTime)<0&&BookingPickOffTime.compareTo(PickoffTime)>0){
                    return false;
                }
            }

        }

        return true;
    }
}