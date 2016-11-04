package com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 原始凭证相关资源文件
 * @author louguanyang
 *
 */
@Entity
public class SourceDocumentResource {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String uuid;

	private String sourceDocumentUuid;
	
	/** 批次号 请求编号 **/
	private String batchNo;
	
	/** 资源文件路径 **/
	private String path;
	
	/** 状态 0:可用,1:不可用 **/
	private boolean status;

	public SourceDocumentResource() {
		super();
	}

	public SourceDocumentResource(String sourceDocumentUuid, String batchNo, String path) {
		super();
		this.uuid = UUID.randomUUID().toString();
		this.sourceDocumentUuid = sourceDocumentUuid;
		this.batchNo = batchNo;
		this.path = path;
		this.status = true;
	}
	
	public SourceDocumentResource(String path) {
		super();
		this.uuid = UUID.randomUUID().toString();
		this.path = path;
		this.status = false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSourceDocumentUuid() {
		return sourceDocumentUuid;
	}

	public void setSourceDocumentUuid(String sourceDocumentUuid) {
		this.sourceDocumentUuid = sourceDocumentUuid;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
