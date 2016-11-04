package com.zufangbao.earth.entity.security;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.demo2do.core.security.details.SecurityUserDetails;
import com.zufangbao.earth.Constant;
import com.zufangbao.earth.RoleSpec;
import com.zufangbao.sun.entity.user.TUser;

/**
 * 
 * @author Downpour
 */
@Entity
@Table(name = "principal")
public class Principal implements SecurityUserDetails {

	private static final long serialVersionUID = -6497354310235849891L;

	@Id
	@GeneratedValue
	private Long id;
	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 权限
	 */
	private String authority;
	
	private Date startDate;
	
	private Date thruDate;
	
	/**
	 * 用户信息
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private TUser tUser;
	
	/**
	 * 创建时间
	 */
	private Date createdTime;
	
	/**
	 * 创建人Id
	 */
	private Long creatorId;
	
	@Transient
	private Map<String, Set<String>> resources = new HashMap<String, Set<String>>();

	@Transient
	private String indexURL;

	/**
	 * default constructor
	 */
	public Principal() {
		
	}
	
	/**
	 * Initialize resources
	 * 
	 * @param resources
	 */
	public void initResources(Map<String, List<String>> resources) {
		
		for(Iterator<Map.Entry<String, List<String>>> iterator = resources.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, List<String>> entry = iterator.next();
			this.addResource(entry.getKey(), entry.getValue());
		}
		
		// get first url as the indexURL
		this.indexURL = resources.get(Constant.URL_RESOURCE).iterator().next();
	
	}
	
	/**
	 * Add resource from key and value
	 * 
	 * @param key
	 * @param values
	 */
	public void addResource(String key, List<String> values) {
		if(this.resources.containsKey(key)) {
			this.resources.get(key).addAll(values);
		} else {
			this.resources.put(key, new LinkedHashSet<String>(values));
		}
	}
	

	/**
	 * Get resource from key
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> getResource(String key) {
		return this.resources.get(key);
	}

	/**
	 * 
	 * @return
	 */
	public String[] getAuthorityCodes() {
		return StringUtils.split(this.authority);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	public String getUsername() {
		return this.name;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
	 */
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList(new String[] { this.authority });
	}
	
	/* (non-Javadoc)
	 * @see com.demo2do.core.security.details.SecurityUserDetails#hasAnyPrincipalRole(java.lang.String[])
	 */
	public boolean hasAnyPrincipalRole(String... roles) {
		
		Set<String> roleSet = AuthorityUtils.authorityListToSet(this.getAuthorities());

        for (String role : roles) {
            if (roleSet.contains(role)) {
                return true;
            }
        }
		
		return false;
		
	}
	
	/* (non-Javadoc)
	 * @see com.demo2do.core.security.details.SecurityUserDetails#hasResource(java.lang.String, java.lang.String)
	 */
	public boolean hasResource(String type, String key) {
		return this.resources.containsKey(type) && this.resources.get(type).contains(key);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 */
	public boolean isAccountNonExpired() {
		
		return thruDate==null;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 */
	public boolean isAccountNonLocked() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	public boolean isEnabled() {
		return true;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the authority
	 */
	public String getAuthority() {
		return authority;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	/**
	 * @return
	 */
	public String getIndexURL() {
		return indexURL;
	}
	
	public boolean can_operate_pay_money(){
		return this.is_super_user_role();
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getThruDate() {
		return thruDate;
	}

	public void setThruDate(Date thruDate) {
		this.thruDate = thruDate;
	}
	
	public TUser gettUser() {
		return tUser;
	}

	public void settUser(TUser tUser) {
		this.tUser = tUser;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public boolean is_super_user_role() {
		return RoleSpec.ROLE_SUPER_USER.equals(getAuthority());
	}

}