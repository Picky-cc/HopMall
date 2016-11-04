/**
 * 
 */
package com.zufangbao.earth.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.demo2do.core.service.GenericService;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.sun.entity.company.corp.App;


/**
 * @author Downpour
 */
public interface PrincipalService extends GenericService<Principal>{
	
	public Principal getPrincipal(String name);
	
	String updatePassword(Principal principal,String oldPassword, String newPassword);

	public void save(Principal newPrincipal);

	public List<App> get_can_access_app_list(Principal principal);

	public Map<String, String> getQueriesByRequest(HttpServletRequest request);
	
	public void deleteUser(Principal newPrincipal);
	
	public Long getSystemPrincipalId();
}
