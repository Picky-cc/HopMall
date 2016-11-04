package com.zufangbao.earth.handler.impl;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.handler.PrincipalHandler;
import com.zufangbao.earth.model.PrincipalInfoModel;
import com.zufangbao.earth.service.PrincipalService;
import com.zufangbao.earth.util.Md5Util;
import com.zufangbao.sun.entity.company.Company;
import com.zufangbao.sun.entity.user.TUser;
import com.zufangbao.sun.service.CompanyService;
import com.zufangbao.sun.yunxin.service.TUserService;

@Component("PrincipalHandler")
public class PrincipalHandlerImpl implements PrincipalHandler{
	
	@Autowired
	private PrincipalService principalService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private TUserService tUserService;

	@Override
	public String createPrincipal(PrincipalInfoModel principalInfoModel, Long creatorId) {
		
		//用户基础信息
		TUser tUser = new TUser();
		tUser.setName(principalInfoModel.getRealname());
		tUser.setEmail(principalInfoModel.getEmail());
		tUser.setPhone(principalInfoModel.getPhone());
		tUser.setDeptName(principalInfoModel.getDeptName());
		tUser.setPositionName(principalInfoModel.getPositionName());
		tUser.setRemark(principalInfoModel.getRemark());
		
		Long companyId = principalInfoModel.getCompanyId();
		if(companyId != null && companyId > 0) {
			Company company = companyService.load(Company.class, principalInfoModel.getCompanyId());
			tUser.setCompany(company);
		}
		//先存储用户基础信息
		Serializable tUserId = tUserService.save(tUser);
		tUser = tUserService.load(TUser.class, tUserId);
		
		//用户账号信息
		Principal principal = new Principal();
		principal.setAuthority(principalInfoModel.getRole());
		principal.setName(principalInfoModel.getUsername());
		
		String randomAlphanumeric = RandomStringUtils.randomAlphanumeric(6);
		principal.setPassword(Md5Util.encode(randomAlphanumeric));
		principal.setCreatedTime(new Date());
		principal.setCreatorId(creatorId);
		
		principal.settUser(tUser);
		
		//存储用户账号信息
		principalService.save(principal);
		return randomAlphanumeric;
	}

	@Override
	public void updatePrincipal(PrincipalInfoModel principalInfoModel,
			Long operatorId) {
		Long principalId = principalInfoModel.getPrincipalId();
		Principal principal = principalService.load(Principal.class, principalId);
		
		TUser tUser = principal.gettUser() == null ? new TUser() : principal.gettUser();
		tUser.setName(principalInfoModel.getRealname());
		tUser.setEmail(principalInfoModel.getEmail());
		tUser.setPhone(principalInfoModel.getPhone());
		tUser.setDeptName(principalInfoModel.getDeptName());
		tUser.setPositionName(principalInfoModel.getPositionName());
		tUser.setRemark(principalInfoModel.getRemark());
		
		Long companyId = principalInfoModel.getCompanyId();
		if(companyId != null && companyId > 0) {
			Company company = companyService.load(Company.class, principalInfoModel.getCompanyId());
			tUser.setCompany(company);
		}
		
		//用户基础信息不存在则创建，有则更新
		Serializable tUserId;
		if(principal.gettUser() == null) {
			tUserId = tUserService.save(tUser);
		}else {
			tUserService.update(tUser);
			tUserId = tUser.getId();
		}
		tUser = tUserService.load(TUser.class, tUserId);
		
		principal.setAuthority(principalInfoModel.getRole());
		principal.settUser(tUser);
		
		principalService.update(principal);
	}
	
}
