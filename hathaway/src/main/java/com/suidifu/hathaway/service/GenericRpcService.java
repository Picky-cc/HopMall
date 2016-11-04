package com.suidifu.hathaway.service;

import java.io.Serializable;
import java.util.List;

import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.suidifu.hathaway.persistence.support.Filter;
import com.suidifu.hathaway.persistence.support.Order;

/**
 * 
 * @author wukai
 *
 * @param <T>
 */
public interface GenericRpcService<T> extends GenericService<T> {
	
	public List<T> loadAllEntity(Class<T> persistentClass);
	
	public List<T> loadOneEntity(Class<T> persistentClass, Long id);
	
	public List<T> listWithPage(Class<T> persistentClass, Page page);
	
	public List<T> listWithFilter(Class<T> persistentClass, Filter filter);
	
	public List<T> listWithFilterPage(Class<T> persistentClass, Filter filter, Page page);
	
	public List<T> listWithOrder(Class<T> persistentClass, Order order);
	
	public List<T> listWithOrderPage(Class<T> persistentClass, Order order, Page page);
	
	public List<T> listWithFilterOrder(Class<T> persistentClass, Filter filter, Order order);
	
	public List<T> listWithFilterOrderPage(Class<T> persistentClass, Filter filter, Order order, Page page);

	public Serializable saveEntity(Object entit,Class<T> persistentClassy);
	
	public void saveOrUpdateEntity(Object entity,Class<T> persistentClass);
	
	public void updateEntity(Object entity,Class<T> persistentClass);
	
	public void deleteEntity(Object entity,Class<T> persistentClass);
	
	public void deleteOneEntity(Class<T> persistentClass, Serializable id);
}
