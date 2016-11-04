/**
 * 
 */
package com.zufangbao.earth.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.utils.StringUtils;
import com.zufangbao.earth.RoleSpec;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.service.PrincipalService;
import com.zufangbao.gluon.opensdk.Md5Util;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.service.AppService;

/**
 * 
 * @author Downpour
 */
@Service("principalService")
public class PrincipalServiceImpl extends GenericServiceImpl<Principal> implements PrincipalService {
	
	@Autowired
	private GenericDaoSupport genericDaoSupport;
	@Autowired
	private AppService appService;
	
	/* (non-Javadoc)
	 * @see com.demo2do.alaska.service.UserService#getPrincipal(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Principal getPrincipal(String name) {
		List<Principal> principals = genericDaoSupport.searchForList("FROM Principal principal WHERE principal.name = :name ", "name", name);
		return CollectionUtils.isNotEmpty(principals) ? principals.get(0) : null; 
	}
	
	@Override
	public String updatePassword(Principal principal, String oldPassword,
			String newPassword) {
		if(!Md5Util.encode(oldPassword).equals(principal.getPassword())){
			return "原密码输入有误";
		}
		principal.setPassword(Md5Util.encode(newPassword));
		try {
			this.genericDaoSupport.update(principal);
			return "修改成功";
		} catch (Exception e) {
			e.printStackTrace();
			return "修改失败请重试";
		}
	}

	@Override
	public void save(Principal newPrincipal) {
		newPrincipal.setStartDate(new Date());
		this.genericDaoSupport.save(newPrincipal);
	}
	
	@Override
	public void deleteUser(Principal newPrincipal){
		newPrincipal.setThruDate(new Date());
		this.genericDaoSupport.update(newPrincipal);
	}
	
	@Override
	public List<App> get_can_access_app_list(Principal principal) {
		if (principal.is_super_user_role() || RoleSpec.ROLE_TRUST_OBSERVER.equals(principal.getAuthority())){
			return getSuperRoleAppList();
		}
		return Collections.emptyList();
	}

	private List<App> getSuperRoleAppList() {
		return appService.list(App.class,new Filter());
	}

	public Map<String, String> getQueriesByRequest(HttpServletRequest request) {
		String queryString = request.getQueryString();
		Map<String, String> queries = StringUtils
				.parseQueryString(queryString);
		if (queries.containsKey("page")) {
			queries.remove("page");
		}
		return queries;
		
	}

	@Override
	public Long getSystemPrincipalId() {
		Filter filter = new Filter();
		filter.addEquals("name", RoleSpec.ROLE_PRINCIPAL_NAME_OF_SYSTEM);
		List<Principal> principalList = this.list(Principal.class, filter);
		if(CollectionUtils.isEmpty(principalList)){
			return null;
		}
		return principalList.get(0).getId();
	}

}
