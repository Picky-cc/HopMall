package com.zufangbao.sun.yunxin.entity.sms;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 短信模版
 * 
 * @author louguanyang
 *
 */
@Entity
@Table(name = "sms_template")
public class SmsTemplate {
	@Id
	@GeneratedValue
	private Long id;
	/** 模版编号 */
	private String code;
	/** 模版名称 */
	private String title;
	/** 模版内容 */
	private String template;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

}
