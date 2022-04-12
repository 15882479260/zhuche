package io.renren.modules.generator.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-04-11 11:18:46
 */
@Data
@TableName(value="tb_order" ,autoResultMap=true)
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单ID
	 */
	@TableId
	private Integer id;

	@TableField(fill = FieldFill.INSERT)
	private Date createtime;


	@TableField(fill = FieldFill.INSERT)
	private Date updatetime;

	/**
	 * 基础信息
	 */
	@TableField(typeHandler = FastjsonTypeHandler.class)
	private JSONObject baseorder;
	/**
	 * 租车人信息
	 */
	@TableField(typeHandler = FastjsonTypeHandler.class)
	private JSONObject contactdetail;
	/**
	 * 费用信息列表
	 */
	@TableField(typeHandler = FastjsonTypeHandler.class)
	private JSONArray amountdetail;
	/**
	 * 车辆信息
	 */
	@TableField(typeHandler = FastjsonTypeHandler.class)
	private JSONObject cardetail;
	/**
	 * 支付状态信息列表
	 */
	@TableField(typeHandler = FastjsonTypeHandler.class)
	private JSONArray paystatusinfos;
	/**
	 * 送车司机信息
	 */
	@TableField(typeHandler = FastjsonTypeHandler.class)
	private JSONObject pickupdriverinfo;
	/**
	 * 取车司机信息
	 */
	@TableField(typeHandler = FastjsonTypeHandler.class)
	private JSONObject pickoffdriverinfo;
	/**
	 * 支付信息列表
	 */
	@TableField(typeHandler = FastjsonTypeHandler.class)
	private JSONArray payinfolist;

}
