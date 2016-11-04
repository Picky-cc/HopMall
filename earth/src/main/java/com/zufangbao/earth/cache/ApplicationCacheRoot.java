package com.zufangbao.earth.cache;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;

import com.demo2do.core.security.details.Menu;
import com.demo2do.core.security.details.Role;
import com.demo2do.core.utils.EnumUtils;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.earth.cache.accessor.PersistAccessor;

/**
 * 
 * @author Downpour
 */
public class ApplicationCacheRoot {
	
	private static final Log logger = LogFactory.getLog(ApplicationCacheRoot.class);
	private PersistAccessor persistAccessor;
	
	/**
	 * @param persistAccessor the persistAccessor to set
	 */
	public void setPersistAccessor(PersistAccessor persistAccessor) {
		this.persistAccessor = persistAccessor;
	}
	
	
	/**
	 * Get all menus as a list
	 * 
	 * @return
	 */
	@Cacheable("menus")
	public List<Menu> getMenus() {
		
		if(logger.isDebugEnabled()) {
			logger.debug("ApplicationCacheRoot#getMenus() - get menus from menus.json");
		}
		
		return JsonUtils.parseArray(new ClassPathResource("menus.json"), Menu.class);
	}
	
	/**
	 * Get all submenus as a list
	 * 
	 * @return
	 */
	@Cacheable("submenus")
	public List<Menu> getSubmenus() {
		
		if(logger.isDebugEnabled()) {
			logger.debug("ApplicationCacheRoot#getSubmenus() - get submenus from submenus.json");
		}
		
		return JsonUtils.parseArray(new ClassPathResource("submenus.json"), Menu.class);
	}
	
	/**
	 * Get all roles group by name and alias
	 * 
	 * @return
	 */
	@Cacheable("roles")
	public Map<String, Role> getRoles() {
		List<Role> roles = JsonUtils.parseArray(new ClassPathResource("roles.json"), Role.class);
		Map<String, Role> result = new LinkedHashMap<String, Role>(roles.size() * 2);
		for(Role role : roles) {
			result.put(role.getName(), role);
			result.put(role.getAlias(), role);
		}
		return result;
	}
	
	/**
	 * 
	 * The actual return type is Map<String, Map<String, List<String>>>
	 * 
	 * @return 
	 */
	@Cacheable("resources")
	public Map<String, Object> getResources() {
		
		if(logger.isDebugEnabled()) {
			logger.debug("ApplicationCacheRoot#getResources() - get resources from resources.json");
		}
		
		return JsonUtils.parse(new ClassPathResource("resources.json"));
	}
	
	/**
	 * Get all the enums
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Cacheable("enums")
	public Map<String, Enum[]> getEnums() {
		return EnumUtils.scan("com.zufangbao");
	}
	
	/**
	 * 
	 * @return
	 */
	public PersistAccessor getData() {
		return this.persistAccessor;
	}

}
