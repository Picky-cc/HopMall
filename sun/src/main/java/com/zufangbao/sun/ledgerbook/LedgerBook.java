package com.zufangbao.sun.ledgerbook;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.demo2do.core.utils.JsonUtils;

@Entity
public class LedgerBook {
	@Id
	@GeneratedValue
	private Long id;
	/**
	 * 账本编号
	 */
	private String ledgerBookNo;
	/**
	 * 账本所属公司
	 */
	private String ledgerBookOrgnizationId;
	private String bookMasterId;
	private String partyConcernedIds;
	public String getLedgerBookNo() {
		return ledgerBookNo;
	}
	public void setLedgerBookNo(String ledgerBookNo) {
		this.ledgerBookNo = ledgerBookNo;
	}
	public String getLedgerBookOrgnizationId() {
		return ledgerBookOrgnizationId;
	}
	public void setLedgerBookOrgnizationId(String ledgerBookOrgnizationId) {
		this.ledgerBookOrgnizationId = ledgerBookOrgnizationId;
	}
	public String getBookMasterId() {
		return bookMasterId;
	}
	public void setBookMasterId(String bookMasterId) {
		this.bookMasterId = bookMasterId;
	}
	public List<String> getPartyConcernedIds() {
		return JsonUtils.parseArray(partyConcernedIds,String.class);
	}
	public void setPartyConcernedIds(List<String> partyConcernedIds) {
		this.partyConcernedIds = JsonUtils.toJsonString(partyConcernedIds);
	}
	public LedgerBook(String ledgerBookNo, String ledgerBookOrgnizationId) {
		super();
		this.ledgerBookNo = ledgerBookNo;
		this.ledgerBookOrgnizationId = ledgerBookOrgnizationId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public LedgerBook(){
		
	}
	
	

}
