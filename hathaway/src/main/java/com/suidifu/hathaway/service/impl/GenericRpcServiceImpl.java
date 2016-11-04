/**
 * 
 */
package com.suidifu.hathaway.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ClassUtils;

import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.utils.JsonUtils;
import com.demo2do.core.web.resolver.Page;
import com.suidifu.hathaway.persistence.support.Filter;
import com.suidifu.hathaway.persistence.support.Order;
import com.suidifu.hathaway.service.GenericRpcService;

/**
 * @author wukai
 *
 */
public class GenericRpcServiceImpl<T> extends GenericServiceImpl<T>  implements GenericRpcService<T> {

	
	@Override
	public List<T> loadAllEntity(Class<T> persistentClass) {
		return genericDaoSupport.searchForList("FROM "+ClassUtils.getShortCanonicalName(persistentClass), "", null);
	}

	@Override
	public List<T> loadOneEntity(Class<T> persistentClass, Long id) {
		return genericDaoSupport.searchForList("FROM "+ClassUtils.getShortCanonicalName(persistentClass) +" WHERE id =:id", "id", id);
	}

	@Override
	public List<T> listWithPage(Class<T> persistentClass, Page page) {
		List<T> result = genericDaoSupport.searchForList("FROM " + ClassUtils.getShortClassName(persistentClass), page.getBeginIndex(), page.getMaxResultRecords());
		page.calculate(result.size());
		return result.size() > page.getEveryPage() ? result.subList(0, page.getEveryPage()) : result;
	
	}

	@Override
	public List<T> listWithFilter(Class<T> persistentClass,Filter filter) {
		return genericDaoSupport.searchForList("FROM " + ClassUtils.getShortClassName(persistentClass) + filter.getWhereSentence(), filter.getParameters());
	}

	@Override
	public List<T> listWithFilterPage(Class<T> persistentClass,Filter filter, Page page) {
		List<T> result = genericDaoSupport.searchForList("FROM " + ClassUtils.getShortClassName(persistentClass) + filter.getWhereSentence(), filter.getParameters(), page.getBeginIndex(), page.getMaxResultRecords());
		page.calculate(result.size());
		return result.size() > page.getEveryPage() ? result.subList(0, page.getEveryPage()) : result; 
	}

	@Override
	public List<T> listWithOrder(Class<T> persistentClass, Order order) {
		List<T> tList =  genericDaoSupport.searchForList("FROM " + ClassUtils.getShortClassName(persistentClass) + order.getSentence());
		return tList;
	}

	@Override
	public List<T> listWithOrderPage(Class<T> persistentClass, Order order,
			Page page) {
		List<T> result = genericDaoSupport.searchForList("FROM " + ClassUtils.getShortClassName(persistentClass) + order.getSentence(), page.getBeginIndex(), page.getMaxResultRecords());
		page.calculate(result.size());
		return result.size() > page.getEveryPage() ? result.subList(0, page.getEveryPage()) : result; 
	}

	@Override
	public List<T> listWithFilterOrder(Class<T> persistentClass,
			com.suidifu.hathaway.persistence.support.Filter filter, Order order) {
		return genericDaoSupport.searchForList("FROM " + ClassUtils.getShortClassName(persistentClass) + filter.getWhereSentence() + order.getSentence(), filter.getParameters());
	}

	@Override
	public List<T> listWithFilterOrderPage(Class<T> persistentClass,
			com.suidifu.hathaway.persistence.support.Filter filter,
			Order order, Page page) {
		List<T> result = genericDaoSupport.searchForList("FROM " + ClassUtils.getShortClassName(persistentClass) + filter.getWhereSentence() + order.getSentence(), filter.getParameters(), page.getBeginIndex(), page.getMaxResultRecords());
		page.calculate(result.size());
		return result.size() > page.getEveryPage() ? result.subList(0, page.getEveryPage()) : result; 
	}

	@Override
	public Serializable saveEntity(Object entity,Class<T> persistentClass) {
		
		T t = JsonUtils.parse(entity.toString(),persistentClass);
		
		return genericDaoSupport.save(t);
	}

	@Override
	public void saveOrUpdateEntity(Object entity,Class<T> persistentClass) {
		T t = JsonUtils.parse(entity.toString(),persistentClass);
		genericDaoSupport.saveOrUpdate(t);
		
	}

	@Override
	public void updateEntity(Object entity,Class<T> persistentClass) {
		T t = JsonUtils.parse(entity.toString(),persistentClass);
		genericDaoSupport.update(t);
	}

	@Override
	public void deleteEntity(Object entity,Class<T> persistentClass) {
		T t = JsonUtils.parse(entity.toString(),persistentClass);
		genericDaoSupport.delete(t);
	}

	@Override
	public void deleteOneEntity(Class<T> persistentClass, Serializable id) {
		genericDaoSupport.delete(persistentClass, id);
	}

}
