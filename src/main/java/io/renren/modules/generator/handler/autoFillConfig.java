package io.renren.modules.generator.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class autoFillConfig implements MetaObjectHandler {

    /**
     * @param
     * @method 插入时自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {

        this.setFieldValByName("createtime",new Date(), metaObject);
        this.setFieldValByName("updatetime",new Date(), metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updatetime",new Date(), metaObject);
    }


}