package com.tul.generator.support;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lengleng
 * @date 2021/7/31
 * <p>
 * 代码生成风格
 */
@Getter
@AllArgsConstructor
public enum StyleTypeEnum {

	/**
	 * 基础模板
	 */
	BASIC("0","基础模板"),

	/**
	 * crud模板
	 */
	CRUD("1", "crud模板");

	/**
	 * 类型
	 */
	private String style;

	/**
	 * 描述
	 */
	private String description;

}
