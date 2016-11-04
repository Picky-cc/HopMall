/**
 * 
 */
package com.zufangbao.sun.service;

import java.util.List;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.customer.CustomerType;

/**
 * @author lute
 *
 */
public interface CustomerService extends GenericService<Customer> {
	
	/**
	 * @param appId
	 * @param customerId
	 * @return
	 */
	public Customer getCustomer(String appId, Long customerId);

	

	public  Filter generateFilterByCondition(String name, String mobile, String contractNo, String app);
	
	public List<Customer> getCustomerListByApp(Page page, App app);
	public Customer getCustomer(App app,CustomerType customerType);
	public Customer getCustomer(String customerUuid);
	
}
