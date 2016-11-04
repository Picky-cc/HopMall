/**
 * 
 */
package com.zufangbao.earth.security;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.demo2do.core.cache.CompositeCacheAccessor;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.service.PrincipalService;

/**
 * 
 * @author downpour
 */
public class ApplicationAuthenticationProvider implements UserDetailsService {

	private static final Log logger = LogFactory.getLog(ApplicationAuthenticationProvider.class);
	
	private CompositeCacheAccessor cacheAccessor;
	
	private PrincipalService principalService;

	/**
	 * @param cacheAccessor the cacheAccessor to set
	 */
	public void setCacheAccessor(CompositeCacheAccessor cacheAccessor) {
		this.cacheAccessor = cacheAccessor;
	}
	
	/**
	 * @param principalService the principalService to set
	 */
	public void setPrincipalService(PrincipalService principalService) {
		this.principalService = principalService;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Principal principal = (Principal) principalService.getPrincipal(username);

		if (principal == null) {
			throw new UsernameNotFoundException("ApplicationAuthenticationProvider#loadUserByUsername() - user not found.");
		}

		logger.info("ApplicationAuthenticationProvider#loadUserByUsername() - user " + principal.getName() + "[" + principal.getAuthority() + "] is found to login. ");
		
		// ======= initialize other user properties =======
		
		// 1. initialize role based resources
		Map<String, List<String>> resources = (Map<String, List<String>>) cacheAccessor.evaluate("resources['" + principal.getAuthority() + "']");
		
		if(MapUtils.isNotEmpty(resources)) {
			principal.initResources(resources);
		}
		return principal;
	}

}
