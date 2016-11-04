package com.zufangbao.sun.service;

import java.util.List;

import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.account.VirtualAccount;
import com.zufangbao.sun.yunxin.entity.model.VirtualAccountModel;

public interface VirtualAccountService extends GenericService<VirtualAccount> {
	public VirtualAccount getVirtualAccountByCustomerUuid(String customerUuid);
	public VirtualAccount create_if_not_exist_virtual_account(String customerUuid, String financialContractUuid, String contractUuid);
	public List<VirtualAccount> get_virtual_accounts_with_balance_gt0();
	public VirtualAccount getVirtualAccountByVirtualAccountUuid(String virtualAccountUuid);
	public List<VirtualAccount> getVirtualAccountList(VirtualAccountModel virtualAccountModel,Page page);
	public List<VirtualAccount> queryVirtualAccountList(String financialContractUuid, Long customerType, String queryKey, Page page);
}
