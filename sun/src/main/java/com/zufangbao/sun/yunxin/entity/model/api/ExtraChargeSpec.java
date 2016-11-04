package com.zufangbao.sun.yunxin.entity.model.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.zufangbao.sun.ledgerbook.ChartOfAccount;

public class ExtraChargeSpec {
	//本金
	public static final String LOAN_ASSET_PRINCIPAL_KEY = ChartOfAccount.SND_UNEARNED_LOAN_ASSET_PRINCIPLE;
	//利息
	public static final String LOAN_ASSET_INTEREST_KEY = ChartOfAccount.SND_UNEARNED_LOAN_ASSET_INTEREST;
	
	//贷款服务费
	public static final String LOAN_SERVICE_FEE_KEY = ChartOfAccount.SND_UNEARNED_LOAN_ASSET_LOAN_SERVICE_FEE;
	//技术服务费
	public static final String LOAN_TECH_FEE_KEY = ChartOfAccount.SND_UNEARNED_LOAN_ASSET_TECH_FEE;
	//其他费用
	public static final String LOAN_OTHER_FEE_KEY = ChartOfAccount.SND_UNEARNED_LOAN_ASSET_OTHER_FEE;
	//罚息
	public static final String PENALTY_KEY = ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY;
	//逾期违约金
	public static final String OVERDUE_FEE_OBLIGATION_KEY = ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OBLIGATION;
	//逾期服务费
	public static final String OVERDUE_FEE_SERVICE_FEE_KEY = ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_SERVICE_FEE;
	//逾期其他费用
	public static final String OVERDUE_FEE_OTHER_FEE_KEY = ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OTHER_FEE;
	
	public static final String TOTAL_OVERDUE_FEE = "TOTAL_OVERDUE_FEE";
	
	public static final String TOTAL_RECEIVABEL_AMOUNT ="TOTAL_RECEIVABEL_AMOUNT";
	
	public static List<String> keySet=new ArrayList<String>()
	{{
		add(LOAN_ASSET_PRINCIPAL_KEY);
		add(LOAN_ASSET_INTEREST_KEY);
		add(LOAN_SERVICE_FEE_KEY);
		add(LOAN_TECH_FEE_KEY);
		add(LOAN_OTHER_FEE_KEY);
		add(PENALTY_KEY);
		add(OVERDUE_FEE_OBLIGATION_KEY);
		add(OVERDUE_FEE_SERVICE_FEE_KEY);
		add(OVERDUE_FEE_OTHER_FEE_KEY);
		
	}};
	
	public static HashMap<String,String> fst_unearned_entry_mapping=new HashMap<String,String>(){{
		put(LOAN_ASSET_PRINCIPAL_KEY,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_PRINCIPLE);
		put(LOAN_ASSET_INTEREST_KEY,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_INTEREST);
		
		put(LOAN_SERVICE_FEE_KEY,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_LOAN_SERVICE_FEE);
		put(LOAN_TECH_FEE_KEY,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_TECH_FEE);
		put(LOAN_OTHER_FEE_KEY,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_OTHER_FEE);
	}};
	
	public static  HashMap<String,String> fst_recievable_entry_mapping=new HashMap<String,String>(){{
		put(LOAN_ASSET_PRINCIPAL_KEY,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_PRINCIPLE);
		put(LOAN_ASSET_INTEREST_KEY,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_INTEREST);
		
		put(LOAN_SERVICE_FEE_KEY,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_LOAN_SERVICE_FEE);
		put(LOAN_TECH_FEE_KEY,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_TECH_FEE);
		put(LOAN_OTHER_FEE_KEY,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_OTHER_FEE);
		
	}};
	public static HashMap<String,String> fst_recievable_overdue_entry_mapping=new HashMap<String,String>(){{
		put(LOAN_ASSET_PRINCIPAL_KEY,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_PRINCIPLE);
		put(LOAN_ASSET_INTEREST_KEY,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_INTEREST);
		
		
		put(LOAN_SERVICE_FEE_KEY,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_LOAN_SERVICE_FEE);
		put(LOAN_TECH_FEE_KEY,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_TECH_FEE);
		put(LOAN_OTHER_FEE_KEY,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_OTHER_FEE);
		put(PENALTY_KEY,ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY);
		put(OVERDUE_FEE_OBLIGATION_KEY,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OBLIGATION);
		put(OVERDUE_FEE_SERVICE_FEE_KEY,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_SERVICE_FEE);
		put(OVERDUE_FEE_OTHER_FEE_KEY,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OTHER_FEE);
	}};
	
}
