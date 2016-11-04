/**
 * 
 */
package com.zufangbao.earth.web.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo2do.core.web.resolver.Secure;
import com.zufangbao.earth.entity.security.Principal;

/**
 * @author lute
 *
 */

@Controller
@RequestMapping("")
public class HomeController {

	/**
	 * Redirect to app renters
	 * 
	 * @return
	 */

	private String getUrlFromPrincipal(String EntryUrl, Principal principal) {
		HashMap<String, String> mapping = HomeControllerSpec
				.HomeEntryMappingByRole().get(EntryUrl);

		String Url = mapping.getOrDefault(principal.getAuthority(),
				mapping.get(HomeControllerSpec.ROLE_DEFAULT));
		return Url;
	}

	@RequestMapping(HomeControllerSpec.DATA)
	public String data(@Secure Principal principal) {

		return getUrlFromPrincipal(HomeControllerSpec.DATA, principal);

	}

	@RequestMapping(HomeControllerSpec.APP_DATA)
	public String appData(@Secure Principal principal) {

		return getUrlFromPrincipal(HomeControllerSpec.APP_DATA, principal);

	}

	@RequestMapping(HomeControllerSpec.FINANCE)
	public String finance(@Secure Principal principal) {
		return getUrlFromPrincipal(HomeControllerSpec.FINANCE, principal);
	}
	
	@RequestMapping(HomeControllerSpec.CAPITAL)
	public String capital(@Secure Principal principal) {
		return getUrlFromPrincipal(HomeControllerSpec.CAPITAL, principal);
	}

	/**
	 * Redirect to service provider
	 * 
	 * @return
	 */

	@RequestMapping(HomeControllerSpec.SYSTEM)
	public String system(@Secure Principal principal) {
		return getUrlFromPrincipal(HomeControllerSpec.SYSTEM, principal);
	}

}
