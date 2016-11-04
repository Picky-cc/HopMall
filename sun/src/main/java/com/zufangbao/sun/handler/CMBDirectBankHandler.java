package com.zufangbao.sun.handler;

import java.util.List;
import java.util.Map;

import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.company.corp.App;


/**
 * 
 * @author zjm
 *
 */
public interface CMBDirectBankHandler extends IDirectBankHandler{

	public int saveNewFlow(App app, Account account, List<Map> flow);
	
}
