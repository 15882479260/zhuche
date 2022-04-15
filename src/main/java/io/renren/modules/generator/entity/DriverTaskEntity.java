package io.renren.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-04-14 09:49:17
 */
@Data
@TableName("tb_driver_task")
public class DriverTaskEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 车型名
	 */
	private String vehiclename;
	/**
	 * 送车或收车
	 */
	private String type;
	/**
	 * 日期时间
	 */
	private String datetime;
	/**
	 * 地址
	 */
	private String address;

	/**
	 * driverId
	 */
	private int driverid;


}
