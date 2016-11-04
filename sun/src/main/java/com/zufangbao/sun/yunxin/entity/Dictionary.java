package com.zufangbao.sun.yunxin.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 字典类
 * 
 * @author louguanyang
 *
 */
@Entity
@Table(name = "dictionary")
public class Dictionary {

	@Id
	@GeneratedValue
	private Long id;
	/**
	 * 字典名称
	 */
	private String code;
	/**
	 * 字典内容
	 */
	private String content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
