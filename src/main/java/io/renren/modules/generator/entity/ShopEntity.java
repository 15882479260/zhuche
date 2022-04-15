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
@TableName("tb_shop")
public class ShopEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 店铺名
	 */
	private String shopname;
	/**
	 * 店铺地址
	 */
	private String shopaddress;
	/**
	 * 经度
	 */
	private Float longitude;
	/**
	 * 纬度
	 */
	private Float latitude;

}
