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
 * @date 2022-03-24 14:28:54
 */
@Data
@TableName("tb_car")
public class CarEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 车辆ID
	 */
	@TableId
	private Integer id;
	/**
	 * 车牌号
	 */
	private String carlicencenum;
	/**
	 * 车型
	 */
	private String vehicle;
	/**
	 * 车架号
	 */
	private String framenum;
	/**
	 * 发动机号
	 */
	private String enginenum;

	/**
	 * 门店
	 */
	private String store;
	/**
	 * 运营状态
	 */
	private String carcurrentstatus;
	/**
	 * 到期提醒日期
	 */
	private String insuranceday;
	/**
	 * 当前车辆里程数
	 */
	private String currentmileage;
	/**
	 * 下次保养公里数
	 */
	private String remainmileage;
	/**
	 * 车型图片
	 */
	private String carmodelimg;
	/**
	 * 审核状态
	 */
	private String reviewstate;
	/**
	 * 车辆颜色
	 */
	private String color;
	/**
	 * 车辆来源
	 */
	private String source;
	/**
	 * 行驶证
	 */
	private String drivinglicense;
	/**
	 * 年检证
	 */
	private String annualinspectioncertificate;
	/**
	 * 商业险保单
	 */
	private String commercialinsurancepolicy;
	/**
	 * 交强险保单
	 */
	private String compulsoryinsurancepolicy;
	/**
	 * 配置信息
	 */
	private String collocation;

}
