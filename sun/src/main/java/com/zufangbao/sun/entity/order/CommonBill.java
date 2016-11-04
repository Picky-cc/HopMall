package com.zufangbao.sun.entity.order;

import java.math.BigDecimal;
import java.util.Date;

import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.customer.Customer;

public interface CommonBill {
	/*----------------------------
	 * 各型账单相同数据访问型方法
	 *---------------------------*/

	/**
	 * id 数据库记录id
	 */
	void setId(Long id);

	Long getId();

	/**
	 * 业务合同的客户customer
	 */
	Customer getCustomer();
	
	void setCustomer(Customer customer);
	
	/**
	 * contract 业务合同
	 */
	Contract getContract();
	
	void setContract(Contract contract);
	
	/**
	 * orderNo 保理约定回款编号
	 */
	void setOrderNo(String orderNo);
	
	String getOrderNo();
	

	/**
	 * 账单应收金额
	 */
	BigDecimal getContractPaymentAmount();

	void setContractPaymentAmount(BigDecimal contractPaymentAmount);

	/**
	 * dueDate 账单或回款应付日期
	 */
	Date getDueDate();

	void setDueDate(Date dueDate);
	
	void setTotalRent(BigDecimal totalRent);
	 
	BigDecimal getTotalRent();
	
}
