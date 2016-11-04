/**
 * 
 */
package com.zufangbao.sun.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.customer.CustomerType;
import com.zufangbao.sun.service.CustomerService;

/**
 * @author lute
 *
 */

@Service("customerService")
public class CustomerServiceImpl extends GenericServiceImpl<Customer> implements CustomerService {
	
	/* (non-Javadoc)
	 * @see com.zufangbao.sun.service.CustomerService#getCustomer(java.lang.String, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Customer getCustomer(String appId, Long customerId) {
		if(StringUtils.isEmpty(appId) || customerId == null){
			return null;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("customerId", customerId);
		parameters.put("appId", appId);
		
		List<Customer> customers = genericDaoSupport.searchForList("FROM Customer customer WHERE customer.id = :customerId AND customer.app.appId = :appId", parameters);
		if(CollectionUtils.isNotEmpty(customers)) {
			return customers.get(0);
		}
		
		return null;
	}
	public List<Customer> getCustomerListByApp(Page page, App app) {
		Filter filter = new Filter();
		filter.addEquals("app", app);
		List<Customer> customerList = list(Customer.class,
				filter, page);
		return customerList;
	}
	private  boolean conditionIsNotEmpty(String name) {
		return !name.isEmpty();
	}
	public  Filter generateFilterByCondition(String name, String mobile, String contractNo, String app) {
		Filter filter = new Filter();
		if(conditionIsNotEmpty(name)){
			filter.addEquals("customer.name", name);
		}
		if(conditionIsNotEmpty(app)){
			filter.addEquals("app.id", Long.valueOf(app));
		}
		if(conditionIsNotEmpty(mobile)){
			filter.addEquals("customer.mobile", mobile);
		}
		if(conditionIsNotEmpty(contractNo)){
			filter.addEquals("contractNo", contractNo);
		}
		return filter;
	}
	@Override
	public Customer getCustomer(App app, CustomerType customerType) {
		Filter filter = new Filter();
		filter.addEquals("app", app);
		filter.addEquals("customerType", customerType);
		List<Customer> customerList = list(Customer.class,filter);
		if(CollectionUtils.isEmpty(customerList)){
			return null;
		}
		return customerList.get(0);
	}
	@Override
	public Customer getCustomer(String customerUuid) {
		Filter filter = new Filter();
		filter.addEquals("customerUuid", customerUuid);
		List<Customer> customerList = list(Customer.class,filter);
		if(CollectionUtils.isEmpty(customerList)){
			return null;
		}
		return customerList.get(0);
	}

}
